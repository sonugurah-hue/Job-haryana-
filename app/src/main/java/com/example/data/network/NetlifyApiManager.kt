package com.example.data.network

import com.example.data.model.JobItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

sealed class NetlifySyncResult {
    data class Success(val count: Int, val jobs: List<JobItem>, val message: String) : NetlifySyncResult()
    data class Failure(val errorMessage: String) : NetlifySyncResult()
}

class NetlifyApiManager {
    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        })
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://netlify.app/") // dummy base, overridden by dynamic @Url
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val service: NetlifyApiService = retrofit.create(NetlifyApiService::class.java)

    suspend fun fetchJobsFromNetlify(apiUrl: String): NetlifySyncResult {
        return withContext(Dispatchers.IO) {
            try {
                val cleanedUrl = apiUrl.trim()
                if (!cleanedUrl.startsWith("http://") && !cleanedUrl.startsWith("https://")) {
                    return@withContext NetlifySyncResult.Failure("Invalid URL: Must start with http:// or https://")
                }

                val response = service.fetchRawJobs(cleanedUrl)
                if (!response.isSuccessful) {
                    val code = response.code()
                    val errorBody = response.errorBody()?.string() ?: ""
                    return@withContext NetlifySyncResult.Failure(
                        "Netlify API responded with HTTP $code: ${errorBody.take(150)}"
                    )
                }

                val rawJson = response.body()?.string()?.trim()
                if (rawJson.isNullOrBlank()) {
                    return@withContext NetlifySyncResult.Failure("Netlify API returned an empty response body.")
                }

                val parsedJobs = parseJsonToJobs(rawJson)
                if (parsedJobs.isEmpty()) {
                    return@withContext NetlifySyncResult.Failure("No valid job objects found in Netlify JSON response.")
                }

                NetlifySyncResult.Success(
                    count = parsedJobs.size,
                    jobs = parsedJobs,
                    message = "Successfully synced ${parsedJobs.size} job(s) from Netlify API."
                )
            } catch (e: Exception) {
                NetlifySyncResult.Failure("Network error connecting to Netlify: ${e.localizedMessage ?: e.message}")
            }
        }
    }

    private fun parseJsonToJobs(rawJson: String): List<JobItem> {
        val trimmed = rawJson.trim()
        val dtoList = mutableListOf<NetlifyJobDto>()

        try {
            if (trimmed.startsWith("{")) {
                // Try object containing "jobs" array
                val adapter = moshi.adapter(NetlifyJobResponse::class.java)
                val resp = adapter.fromJson(trimmed)
                resp?.jobs?.let { dtoList.addAll(it) }
            } else if (trimmed.startsWith("[")) {
                // Array of jobs
                val listType = Types.newParameterizedType(List::class.java, NetlifyJobDto::class.java)
                val adapter = moshi.adapter<List<NetlifyJobDto>>(listType)
                adapter.fromJson(trimmed)?.let { dtoList.addAll(it) }
            }
        } catch (e: Exception) {
            // Parsing failure fallback
            e.printStackTrace()
        }

        return dtoList.map { it.toJobItem() }
    }
}
