package com.example.data.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface NetlifyApiService {
    @GET
    suspend fun fetchRawJobs(@Url url: String): Response<ResponseBody>
}
