package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.NavCategory
import com.example.ui.components.InfoDialog
import com.example.ui.components.JobDetailScreen
import com.example.ui.components.JobHeader
import com.example.ui.components.JobNavigationMenu
import com.example.ui.components.JobSearchBar
import com.example.ui.components.NetlifyApiDialog
import com.example.ui.screens.CategoryListScreen
import com.example.ui.screens.HomeScreenContent
import com.example.ui.theme.JobHaryanaTheme
import com.example.ui.theme.PortalBackground
import com.example.ui.viewmodel.JobHaryanaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JobHaryanaTheme {
                JobHaryanaApp()
            }
        }
    }
}

@Composable
fun JobHaryanaApp(
    viewModel: JobHaryanaViewModel = viewModel()
) {
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val qualFilter by viewModel.selectedQualificationFilter.collectAsStateWithLifecycle()
    val selectedJobDetail by viewModel.selectedJobDetail.collectAsStateWithLifecycle()
    val activeInfoDialog by viewModel.activeInfoDialog.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val statusMessage by viewModel.statusMessage.collectAsStateWithLifecycle()

    val showNetlifyDialog by viewModel.showNetlifyDialog.collectAsStateWithLifecycle()
    val netlifyUrl by viewModel.netlifyUrl.collectAsStateWithLifecycle()
    val netlifySyncStatus by viewModel.netlifySyncStatus.collectAsStateWithLifecycle()
    val netlifyLastSyncTime by viewModel.netlifyLastSyncTime.collectAsStateWithLifecycle()
    val isNetlifySyncing by viewModel.isNetlifySyncing.collectAsStateWithLifecycle()

    val allJobs by viewModel.allJobs.collectAsStateWithLifecycle()
    val currentDisplayJobs by viewModel.currentDisplayJobs.collectAsStateWithLifecycle()
    val bookmarkedJobs by viewModel.bookmarkedJobs.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(statusMessage) {
        statusMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearStatusMessage()
        }
    }

    // Hardware/Gesture Back Navigation Handling
    BackHandler(enabled = selectedJobDetail != null || selectedTab != NavCategory.HOME) {
        if (selectedJobDetail != null) {
            viewModel.closeJobDetail()
        } else if (selectedTab != NavCategory.HOME) {
            viewModel.selectTab(NavCategory.HOME)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = PortalBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (selectedJobDetail != null) {
                // Job Detail Screen View
                JobDetailScreen(
                    job = selectedJobDetail!!,
                    onBackClick = { viewModel.closeJobDetail() },
                    onBookmarkToggle = { viewModel.toggleBookmark(it) }
                )
            } else {
                // Main Portal View
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PortalBackground)
                ) {
                    // Header
                    JobHeader(
                        bookmarkCount = bookmarkedJobs.size,
                        isRefreshing = isRefreshing,
                        onRefreshClick = { viewModel.refreshJobUpdates() },
                        onBookmarkClick = { viewModel.selectTab(NavCategory.BOOKMARKS) },
                        onNetlifyClick = { viewModel.openNetlifySettings() },
                        onInfoClick = { viewModel.openInfoDialog(it) }
                    )

                    // Navigation Bar (Home, Latest Jobs, Haryana Jobs, Central Govt Jobs, Admission, Admit Card, Result, Answer Key)
                    JobNavigationMenu(
                        selectedCategory = selectedTab,
                        onCategorySelected = { viewModel.selectTab(it) }
                    )

                    // Search and Filter Bar
                    JobSearchBar(
                        query = searchQuery,
                        onQueryChange = { viewModel.updateSearchQuery(it) },
                        selectedFilter = qualFilter,
                        onFilterSelect = { viewModel.setQualificationFilter(it) }
                    )

                    // Content Switcher
                    val isDefaultHomeView = selectedTab == NavCategory.HOME &&
                            searchQuery.isBlank() &&
                            qualFilter == "All"

                    AnimatedContent(
                        targetState = isDefaultHomeView,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "MainContentTransition",
                        modifier = Modifier.weight(1f)
                    ) { defaultHome ->
                        if (defaultHome) {
                            HomeScreenContent(
                                jobs = allJobs,
                                onViewDetailsClick = { viewModel.openJobDetail(it) },
                                onBookmarkToggle = { viewModel.toggleBookmark(it) },
                                onCategoryTabSelect = { viewModel.selectTab(it) },
                                onInfoDialogClick = { viewModel.openInfoDialog(it) }
                            )
                        } else {
                            CategoryListScreen(
                                category = selectedTab,
                                jobs = currentDisplayJobs,
                                onViewDetailsClick = { viewModel.openJobDetail(it) },
                                onBookmarkToggle = { viewModel.toggleBookmark(it) },
                                onBackToHome = {
                                    viewModel.selectTab(NavCategory.HOME)
                                    viewModel.updateSearchQuery("")
                                    viewModel.setQualificationFilter("All")
                                },
                                onClearFilters = {
                                    viewModel.updateSearchQuery("")
                                    viewModel.setQualificationFilter("All")
                                }
                            )
                        }
                    }
                }
            }

            // Info Dialogs (About Us, Contact, Disclaimer, Privacy Policy)
            activeInfoDialog?.let { dialogType ->
                InfoDialog(
                    type = dialogType,
                    onDismiss = { viewModel.closeInfoDialog() }
                )
            }

            // Netlify API Configuration Dialog
            if (showNetlifyDialog) {
                NetlifyApiDialog(
                    currentUrl = netlifyUrl,
                    syncStatus = netlifySyncStatus,
                    lastSyncTime = netlifyLastSyncTime,
                    isSyncing = isNetlifySyncing,
                    onUrlSave = { viewModel.updateNetlifyUrl(it) },
                    onResetDefault = { viewModel.resetNetlifyUrl() },
                    onSyncNow = { viewModel.syncFromNetlify(it) },
                    onDismiss = { viewModel.closeNetlifySettings() }
                )
            }
        }
    }
}
