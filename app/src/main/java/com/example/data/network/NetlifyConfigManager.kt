package com.example.data.network

import android.content.Context
import android.content.SharedPreferences

class NetlifyConfigManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("job_haryana_netlify_prefs", Context.MODE_PRIVATE)

    companion object {
        const val DEFAULT_NETLIFY_URL = "https://job-haryana.netlify.app/.netlify/functions/jobs"
        private const val KEY_NETLIFY_URL = "key_netlify_url"
        private const val KEY_LAST_SYNC_TIME = "key_last_sync_time"
        private const val KEY_LAST_SYNC_COUNT = "key_last_sync_count"
        private const val KEY_LAST_SYNC_STATUS = "key_last_sync_status"
    }

    var netlifyApiUrl: String
        get() = prefs.getString(KEY_NETLIFY_URL, DEFAULT_NETLIFY_URL) ?: DEFAULT_NETLIFY_URL
        set(value) = prefs.edit().putString(KEY_NETLIFY_URL, value.trim()).apply()

    var lastSyncTimestamp: Long
        get() = prefs.getLong(KEY_LAST_SYNC_TIME, 0L)
        set(value) = prefs.edit().putLong(KEY_LAST_SYNC_TIME, value).apply()

    var lastSyncCount: Int
        get() = prefs.getInt(KEY_LAST_SYNC_COUNT, 0)
        set(value) = prefs.edit().putInt(KEY_LAST_SYNC_COUNT, value).apply()

    var lastSyncStatus: String
        get() = prefs.getString(KEY_LAST_SYNC_STATUS, "Not yet connected") ?: "Not yet connected"
        set(value) = prefs.edit().putString(KEY_LAST_SYNC_STATUS, value).apply()

    fun resetToDefault() {
        netlifyApiUrl = DEFAULT_NETLIFY_URL
    }
}
