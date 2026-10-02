package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.JobItem
import com.example.data.model.NavCategory
import com.example.data.repository.JobRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class InfoDialogType {
    ABOUT_US,
    CONTACT_US,
    DISCLAIMER,
    PRIVACY_POLICY
}

class JobHaryanaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = JobRepository(application)

    private val _selectedTab = MutableStateFlow(NavCategory.HOME)
    val selectedTab: StateFlow<NavCategory> = _selectedTab.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedQualificationFilter = MutableStateFlow("All")
    val selectedQualificationFilter: StateFlow<String> = _selectedQualificationFilter.asStateFlow()

    private val _selectedJobDetail = MutableStateFlow<JobItem?>(null)
    val selectedJobDetail: StateFlow<JobItem?> = _selectedJobDetail.asStateFlow()

    private val _activeInfoDialog = MutableStateFlow<InfoDialogType?>(null)
    val activeInfoDialog: StateFlow<InfoDialogType?> = _activeInfoDialog.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    // Netlify API Configuration & Sync State
    private val _showNetlifyDialog = MutableStateFlow(false)
    val showNetlifyDialog: StateFlow<Boolean> = _showNetlifyDialog.asStateFlow()

    private val _netlifyUrl = MutableStateFlow(repository.netlifyConfig.netlifyApiUrl)
    val netlifyUrl: StateFlow<String> = _netlifyUrl.asStateFlow()

    private val _netlifySyncStatus = MutableStateFlow(repository.netlifyConfig.lastSyncStatus)
    val netlifySyncStatus: StateFlow<String> = _netlifySyncStatus.asStateFlow()

    private val _netlifyLastSyncTime = MutableStateFlow(repository.netlifyConfig.lastSyncTimestamp)
    val netlifyLastSyncTime: StateFlow<Long> = _netlifyLastSyncTime.asStateFlow()

    private val _isNetlifySyncing = MutableStateFlow(false)
    val isNetlifySyncing: StateFlow<Boolean> = _isNetlifySyncing.asStateFlow()

    val allJobs: StateFlow<List<JobItem>> = repository.allJobs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val featuredJobs: StateFlow<List<JobItem>> = repository.featuredJobs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarkedJobs: StateFlow<List<JobItem>> = repository.bookmarkedJobs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Combined filtered stream based on tab, search query, and qualification tag
    val currentDisplayJobs: StateFlow<List<JobItem>> = combine(
        allJobs,
        _selectedTab,
        _searchQuery,
        _selectedQualificationFilter
    ) { jobs, tab, query, qualFilter ->
        var list = jobs

        // 1. Filter by Nav Tab
        list = when (tab) {
            NavCategory.HOME -> list
            NavCategory.LATEST_JOBS -> list.filter { it.category == NavCategory.HARYANA_JOBS.id || it.category == NavCategory.CENTRAL_JOBS.id || it.isFeatured }
            NavCategory.HARYANA_JOBS -> list.filter { it.category == NavCategory.HARYANA_JOBS.id }
            NavCategory.CENTRAL_JOBS -> list.filter { it.category == NavCategory.CENTRAL_JOBS.id }
            NavCategory.ADMISSION -> list.filter { it.category == NavCategory.ADMISSION.id }
            NavCategory.ADMIT_CARD -> list.filter { it.category == NavCategory.ADMIT_CARD.id }
            NavCategory.RESULT -> list.filter { it.category == NavCategory.RESULT.id }
            NavCategory.ANSWER_KEY -> list.filter { it.category == NavCategory.ANSWER_KEY.id }
            NavCategory.BOOKMARKS -> list.filter { it.isBookmarked }
        }

        // 2. Filter by search query
        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter {
                it.title.lowercase().contains(q) ||
                        it.hindiTitle.lowercase().contains(q) ||
                        it.organization.lowercase().contains(q) ||
                        it.qualification.lowercase().contains(q) ||
                        it.tags.lowercase().contains(q)
            }
        }

        // 3. Filter by qualification chip
        if (qualFilter != "All") {
            val qTag = qualFilter.lowercase()
            list = list.filter {
                it.qualification.lowercase().contains(qTag) ||
                        it.tags.lowercase().contains(qTag)
            }
        }

        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.checkAndSeedInitialData()
        }
    }

    fun selectTab(category: NavCategory) {
        _selectedTab.value = category
        // Clear search if switching tabs to give fresh view
        _searchQuery.value = ""
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setQualificationFilter(filter: String) {
        _selectedQualificationFilter.value = filter
    }

    fun openJobDetail(job: JobItem) {
        _selectedJobDetail.value = job
    }

    fun closeJobDetail() {
        _selectedJobDetail.value = null
    }

    fun toggleBookmark(job: JobItem) {
        viewModelScope.launch {
            repository.toggleBookmark(job.id, job.isBookmarked)
            val newStatus = !job.isBookmarked
            _statusMessage.value = if (newStatus) "नौकरी सेव की गई (Job Saved)" else "बुकमार्क हटाया गया (Removed)"
            // Also update selectedJobDetail if open
            if (_selectedJobDetail.value?.id == job.id) {
                _selectedJobDetail.value = _selectedJobDetail.value?.copy(isBookmarked = newStatus)
            }
        }
    }

    fun refreshJobUpdates() {
        viewModelScope.launch {
            _isRefreshing.value = true
            val count = repository.syncLatestUpdates()
            _isRefreshing.value = false
            _statusMessage.value = "नवीनतम अपडेट्स लोड हो गए हैं ($count उपलब्ध)"
        }
    }

    fun openInfoDialog(type: InfoDialogType) {
        _activeInfoDialog.value = type
    }

    fun closeInfoDialog() {
        _activeInfoDialog.value = null
    }

    // --- Netlify API Methods ---
    fun openNetlifySettings() {
        _showNetlifyDialog.value = true
        _netlifyUrl.value = repository.netlifyConfig.netlifyApiUrl
        _netlifySyncStatus.value = repository.netlifyConfig.lastSyncStatus
        _netlifyLastSyncTime.value = repository.netlifyConfig.lastSyncTimestamp
    }

    fun closeNetlifySettings() {
        _showNetlifyDialog.value = false
    }

    fun updateNetlifyUrl(newUrl: String) {
        _netlifyUrl.value = newUrl
        repository.netlifyConfig.netlifyApiUrl = newUrl
    }

    fun resetNetlifyUrl() {
        repository.netlifyConfig.resetToDefault()
        _netlifyUrl.value = repository.netlifyConfig.netlifyApiUrl
        _statusMessage.value = "Netlify API URL reset to default"
    }

    fun syncFromNetlify(customUrl: String? = null) {
        viewModelScope.launch {
            _isNetlifySyncing.value = true
            val urlToUse = customUrl ?: _netlifyUrl.value
            val result = repository.syncFromNetlifyApi(urlToUse)
            _isNetlifySyncing.value = false

            _netlifySyncStatus.value = repository.netlifyConfig.lastSyncStatus
            _netlifyLastSyncTime.value = repository.netlifyConfig.lastSyncTimestamp

            when (result) {
                is com.example.data.network.NetlifySyncResult.Success -> {
                    _statusMessage.value = "✅ Netlify API: ${result.count} jobs synced successfully!"
                }
                is com.example.data.network.NetlifySyncResult.Failure -> {
                    _statusMessage.value = "❌ Netlify API Sync Error: ${result.errorMessage}"
                }
            }
        }
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }
}
