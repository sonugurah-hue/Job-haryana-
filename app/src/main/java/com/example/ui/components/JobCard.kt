package com.example.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.JobItem
import com.example.ui.theme.AlertRed
import com.example.ui.theme.AlertRedContainer
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldGovtGreen
import com.example.ui.theme.HaryanaNavyContainer
import com.example.ui.theme.HaryanaNavyDark
import com.example.ui.theme.HaryanaNavyPrimary
import com.example.ui.theme.PortalCardBorder
import com.example.ui.theme.SaffronAccent
import com.example.ui.theme.SaffronContainer
import com.example.ui.theme.SaffronOnContainer

@Composable
fun JobCard(
    job: JobItem,
    onViewDetailsClick: (JobItem) -> Unit,
    onBookmarkToggle: (JobItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onViewDetailsClick(job) }
            .testTag("job_card_${job.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        border = BorderStroke(1.dp, PortalCardBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Top Badge Header: Organization + Badge Type (NEW/URGENT/EXTENDED) + Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Organization pill
                Surface(
                    color = HaryanaNavyContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = job.organization,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = HaryanaNavyDark,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Badge Chip (NEW, URGENT, EXTENDED, etc.)
                    val (badgeBg, badgeFg) = when (job.badgeType) {
                        "URGENT" -> AlertRedContainer to AlertRed
                        "EXTENDED" -> SaffronContainer to SaffronOnContainer
                        "POPULAR" -> Color(0xFFF3E8FF) to Color(0xFF7E22CE)
                        else -> EmeraldContainer to EmeraldGovtGreen
                    }

                    Surface(
                        color = badgeBg,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = when (job.badgeType) {
                                "URGENT" -> "⚠️ जरूरी / Urgent"
                                "EXTENDED" -> "⏳ तिथि बढ़ी"
                                "POPULAR" -> "⭐ लोकप्रिय"
                                else -> "✨ नया / New"
                            },
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = badgeFg,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Bookmark Button
                    IconButton(
                        onClick = { onBookmarkToggle(job) },
                        modifier = Modifier
                            .size(34.dp)
                            .testTag("bookmark_button_${job.id}")
                    ) {
                        Icon(
                            imageVector = if (job.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = if (job.isBookmarked) "Bookmarked" else "Bookmark",
                            tint = if (job.isBookmarked) SaffronAccent else Color(0xFF94A3B8),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Share Button
                    IconButton(
                        onClick = { shareJob(context, job) },
                        modifier = Modifier
                            .size(34.dp)
                            .testTag("share_button_${job.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Job",
                            tint = Color(0xFF64748B),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Job Title in English & Hindi
            Text(
                text = job.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                ),
                color = Color(0xFF0F172A),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            if (job.hindiTitle.isNotBlank()) {
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = job.hindiTitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        color = Color(0xFF1E3A8A)
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Spec Box: Total Vacancies, Last Date, Qualification
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF8FAFC))
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Total Vacancies
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Group,
                        contentDescription = "Vacancies",
                        tint = HaryanaNavyPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Total Vacancies (कुल पद): ",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF475569)
                        )
                    )
                    Text(
                        text = job.totalVacancies,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                    )
                }

                // Last Date
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Last Date",
                        tint = AlertRed,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Last Date (अंतिम तिथि): ",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF475569)
                        )
                    )
                    Text(
                        text = job.lastDate,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = AlertRed
                        )
                    )
                }

                // Qualification
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "Qualification",
                        tint = EmeraldGovtGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Qualification (योग्यता): ",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF475569)
                        )
                    )
                    Text(
                        text = job.qualification,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF0F172A)
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action: "View Details" Button
            Button(
                onClick = { onViewDetailsClick(job) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("view_details_button_${job.id}"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HaryanaNavyPrimary,
                    contentColor = Color.White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "View Details (विवरण देखें)",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

private fun shareJob(context: Context, job: JobItem) {
    val shareText = buildString {
        append("📢 *JOB HARYANA NOTIFICATION UPDATE*\n\n")
        append("📌 *${job.title}*\n")
        append("🇮🇳 ${job.hindiTitle}\n\n")
        append("🏢 विभाग/संस्था: ${job.organization}\n")
        append("👥 कुल पद: ${job.totalVacancies}\n")
        append("📅 अंतिम तिथि: ${job.lastDate}\n")
        append("🎓 योग्यता: ${job.qualification}\n")
        append("💰 वेतनमान: ${job.salary}\n\n")
        append("🔗 ऑनलाइन आवेदन / नोटिफिकेशन लिंक:\n${job.applyOnlineUrl}\n\n")
        append("📲 सभी ताजा हरियाणा व केंद्र सरकारी नौकरियों के लिए 'Job Haryana' ऐप डाउनलोड करें।")
    }

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, job.title)
        putExtra(Intent.EXTRA_TEXT, shareText)
    }
    context.startActivity(Intent.createChooser(intent, "Job Haryana - Share via"))
}
