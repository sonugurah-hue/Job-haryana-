package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.JobItem
import com.example.data.model.NavCategory
import com.example.ui.components.JobCard
import com.example.ui.theme.AlertRed
import com.example.ui.theme.EmeraldGovtGreen
import com.example.ui.theme.HaryanaNavyDark
import com.example.ui.theme.HaryanaNavyPrimary
import com.example.ui.theme.PortalBackground
import com.example.ui.theme.SaffronAccent

@Composable
fun CategoryListScreen(
    category: NavCategory,
    jobs: List<JobItem>,
    onViewDetailsClick: (JobItem) -> Unit,
    onBookmarkToggle: (JobItem) -> Unit,
    onBackToHome: () -> Unit,
    onClearFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryIcon = when (category) {
        NavCategory.HOME -> Icons.Default.Home
        NavCategory.LATEST_JOBS -> Icons.Default.Whatshot
        NavCategory.HARYANA_JOBS -> Icons.Default.LocationCity
        NavCategory.CENTRAL_JOBS -> Icons.Default.Public
        NavCategory.ADMISSION -> Icons.Default.School
        NavCategory.ADMIT_CARD -> Icons.Default.CardMembership
        NavCategory.RESULT -> Icons.Default.CheckCircleOutline
        NavCategory.ANSWER_KEY -> Icons.Default.Key
        NavCategory.BOOKMARKS -> Icons.Default.Bookmark
    }

    val categoryColor = when (category) {
        NavCategory.LATEST_JOBS -> AlertRed
        NavCategory.HARYANA_JOBS -> HaryanaNavyPrimary
        NavCategory.CENTRAL_JOBS -> SaffronAccent
        NavCategory.ADMISSION -> Color(0xFF0284C7)
        NavCategory.ADMIT_CARD -> Color(0xFF7E22CE)
        NavCategory.RESULT -> EmeraldGovtGreen
        NavCategory.ANSWER_KEY -> Color(0xFFEA580C)
        NavCategory.BOOKMARKS -> SaffronAccent
        else -> HaryanaNavyPrimary
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PortalBackground)
    ) {
        // Section Header Banner
        Card(
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        color = categoryColor.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = categoryIcon,
                                contentDescription = null,
                                tint = categoryColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = category.titleEn,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = category.titleHi,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = Color(0xFF64748B)
                            )
                        )
                    }
                }

                Surface(
                    color = Color(0xFFF1F5F9),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "${jobs.size} सूचनाएं / Posts",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        ),
                        color = HaryanaNavyDark,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        if (jobs.isEmpty()) {
            // Empty State
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Inbox,
                    contentDescription = "No Jobs Found",
                    tint = Color(0xFF94A3B8),
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (category == NavCategory.BOOKMARKS) "कोई नौकरी सेव नहीं की गई है" else "इस श्रेणी में कोई परिणाम नहीं मिला",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF334155),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = if (category == NavCategory.BOOKMARKS) "नौकरी कार्ड पर बने बुकमार्क (❤️) आइकन पर क्लिक करके आप ऑफलाइन पढ़ सकते हैं।" else "कृपया अन्य सर्च शब्द या योग्यता फ़िल्टर बदलकर पुनः प्रयास करें।",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = onClearFilters) {
                        Text("फ़िल्टर हटाएं (Reset)")
                    }
                    Button(
                        onClick = onBackToHome,
                        colors = ButtonDefaults.buttonColors(containerColor = HaryanaNavyPrimary)
                    ) {
                        Text("होम पेज (Home)")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(jobs, key = { it.id }) { job ->
                    JobCard(
                        job = job,
                        onViewDetailsClick = onViewDetailsClick,
                        onBookmarkToggle = onBookmarkToggle
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
