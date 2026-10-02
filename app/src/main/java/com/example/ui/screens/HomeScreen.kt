package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.JobItem
import com.example.data.model.NavCategory
import com.example.ui.components.JobCard
import com.example.ui.components.JobFooter
import com.example.ui.theme.AlertRed
import com.example.ui.theme.EmeraldGovtGreen
import com.example.ui.theme.HaryanaNavyPrimary
import com.example.ui.theme.PortalBackground
import com.example.ui.theme.SaffronAccent
import com.example.ui.viewmodel.InfoDialogType

@Composable
fun HomeScreenContent(
    jobs: List<JobItem>,
    onViewDetailsClick: (JobItem) -> Unit,
    onBookmarkToggle: (JobItem) -> Unit,
    onCategoryTabSelect: (NavCategory) -> Unit,
    onInfoDialogClick: (InfoDialogType) -> Unit,
    modifier: Modifier = Modifier
) {
    // Partition jobs into sections
    val latestJobs = jobs.filter { it.isFeatured || it.category == NavCategory.HARYANA_JOBS.id || it.category == NavCategory.CENTRAL_JOBS.id }.take(4)
    val haryanaJobs = jobs.filter { it.category == NavCategory.HARYANA_JOBS.id }.take(3)
    val centralJobs = jobs.filter { it.category == NavCategory.CENTRAL_JOBS.id }.take(3)
    val admitCards = jobs.filter { it.category == NavCategory.ADMIT_CARD.id }.take(3)
    val results = jobs.filter { it.category == NavCategory.RESULT.id }.take(3)
    val admissions = jobs.filter { it.category == NavCategory.ADMISSION.id }.take(3)
    val answerKeys = jobs.filter { it.category == NavCategory.ANSWER_KEY.id }.take(2)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(PortalBackground),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Portal Quick Metrics Banner
        item {
            PortalStatsBar(totalActiveListings = jobs.size)
        }

        // Section 1: Latest Jobs (Prominent)
        if (latestJobs.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "Latest Jobs",
                    hindiTitle = "नवीनतम नौकरियां",
                    icon = Icons.Default.Whatshot,
                    iconTint = AlertRed,
                    onViewAllClick = { onCategoryTabSelect(NavCategory.LATEST_JOBS) }
                )
            }

            items(latestJobs, key = { "latest_${it.id}" }) { job ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    JobCard(
                        job = job,
                        onViewDetailsClick = onViewDetailsClick,
                        onBookmarkToggle = onBookmarkToggle
                    )
                }
            }
        }

        // Section 2: Haryana Government Jobs
        if (haryanaJobs.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                SectionHeader(
                    title = "Haryana Government Jobs",
                    hindiTitle = "हरियाणा सरकारी नौकरी (HSSC / HPSC / HKRN)",
                    icon = Icons.Default.LocationCity,
                    iconTint = HaryanaNavyPrimary,
                    onViewAllClick = { onCategoryTabSelect(NavCategory.HARYANA_JOBS) }
                )
            }

            items(haryanaJobs, key = { "hry_${it.id}" }) { job ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    JobCard(
                        job = job,
                        onViewDetailsClick = onViewDetailsClick,
                        onBookmarkToggle = onBookmarkToggle
                    )
                }
            }
        }

        // Section 3: Central Government Jobs
        if (centralJobs.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                SectionHeader(
                    title = "Central Government Jobs",
                    hindiTitle = "केंद्रीय सरकारी नौकरियां (SSC / Railway / Banking)",
                    icon = Icons.Default.Public,
                    iconTint = SaffronAccent,
                    onViewAllClick = { onCategoryTabSelect(NavCategory.CENTRAL_JOBS) }
                )
            }

            items(centralJobs, key = { "central_${it.id}" }) { job ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    JobCard(
                        job = job,
                        onViewDetailsClick = onViewDetailsClick,
                        onBookmarkToggle = onBookmarkToggle
                    )
                }
            }
        }

        // Section 4: Admit Card
        if (admitCards.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                SectionHeader(
                    title = "Admit Card",
                    hindiTitle = "प्रवेश पत्र / हॉल टिकट",
                    icon = Icons.Default.CardMembership,
                    iconTint = Color(0xFF7E22CE),
                    onViewAllClick = { onCategoryTabSelect(NavCategory.ADMIT_CARD) }
                )
            }

            items(admitCards, key = { "admit_${it.id}" }) { job ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    JobCard(
                        job = job,
                        onViewDetailsClick = onViewDetailsClick,
                        onBookmarkToggle = onBookmarkToggle
                    )
                }
            }
        }

        // Section 5: Results
        if (results.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                SectionHeader(
                    title = "Results & Cutoff",
                    hindiTitle = "परीक्षा परिणाम व कटऑफ सूची",
                    icon = Icons.Default.CheckCircleOutline,
                    iconTint = EmeraldGovtGreen,
                    onViewAllClick = { onCategoryTabSelect(NavCategory.RESULT) }
                )
            }

            items(results, key = { "res_${it.id}" }) { job ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    JobCard(
                        job = job,
                        onViewDetailsClick = onViewDetailsClick,
                        onBookmarkToggle = onBookmarkToggle
                    )
                }
            }
        }

        // Section 6: Admissions
        if (admissions.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                SectionHeader(
                    title = "Admissions",
                    hindiTitle = "कॉलेज व यूनिवर्सिटी एडमिशन (DHE / KUK / MDU)",
                    icon = Icons.Default.School,
                    iconTint = Color(0xFF0284C7),
                    onViewAllClick = { onCategoryTabSelect(NavCategory.ADMISSION) }
                )
            }

            items(admissions, key = { "adm_${it.id}" }) { job ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    JobCard(
                        job = job,
                        onViewDetailsClick = onViewDetailsClick,
                        onBookmarkToggle = onBookmarkToggle
                    )
                }
            }
        }

        // Section 7: Answer Key
        if (answerKeys.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                SectionHeader(
                    title = "Answer Key",
                    hindiTitle = "उत्तर कुंजी व प्रश्न पत्र आपत्ति",
                    icon = Icons.Default.Key,
                    iconTint = Color(0xFFEA580C),
                    onViewAllClick = { onCategoryTabSelect(NavCategory.ANSWER_KEY) }
                )
            }

            items(answerKeys, key = { "key_${it.id}" }) { job ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    JobCard(
                        job = job,
                        onViewDetailsClick = onViewDetailsClick,
                        onBookmarkToggle = onBookmarkToggle
                    )
                }
            }
        }

        // Full Portal Footer
        item {
            Spacer(modifier = Modifier.height(16.dp))
            JobFooter(
                onNavSelect = onCategoryTabSelect,
                onInfoDialogClick = onInfoDialogClick
            )
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    hindiTitle: String,
    icon: ImageVector,
    iconTint: Color,
    onViewAllClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Surface(
                color = iconTint.copy(alpha = 0.12f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(34.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = hindiTitle,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = Color(0xFF64748B)
                )
            }
        }

        Row(
            modifier = Modifier
                .clickable(onClick = onViewAllClick)
                .padding(vertical = 4.dp, horizontal = 6.dp)
                .testTag("view_all_${title.replace(" ", "_")}"),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "View All (सभी)",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                ),
                color = HaryanaNavyPrimary
            )
            Spacer(modifier = Modifier.width(2.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "View All",
                tint = HaryanaNavyPrimary,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
fun PortalStatsBar(totalActiveListings: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF1F5F9)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatsItem(label = "Active Updates", value = "$totalActiveListings+ पद व सूचनाएं")
            StatsDivider()
            StatsItem(label = "Coverage", value = "हरियाणा + केंद्र")
            StatsDivider()
            StatsItem(label = "Language", value = "हिंदी व English")
        }
    }
}

@Composable
fun StatsItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = HaryanaNavyPrimary
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                color = Color(0xFF64748B)
            )
        )
    }
}

@Composable
fun StatsDivider() {
    Box(
        modifier = Modifier
            .height(24.dp)
            .width(1.dp)
            .background(Color(0xFFCBD5E1))
    )
}
