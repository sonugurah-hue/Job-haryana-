package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.JobItem
import com.example.ui.theme.AlertRed
import com.example.ui.theme.AlertRedContainer
import com.example.ui.theme.EmeraldGovtGreen
import com.example.ui.theme.HaryanaNavyContainer
import com.example.ui.theme.HaryanaNavyDark
import com.example.ui.theme.HaryanaNavyPrimary
import com.example.ui.theme.PortalBackground
import com.example.ui.theme.PortalCardBorder
import com.example.ui.theme.SaffronAccent
import com.example.ui.theme.SaffronContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailScreen(
    job: JobItem,
    onBackClick: () -> Unit,
    onBookmarkToggle: (JobItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    BackHandler { onBackClick() }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Job Details (नौकरी विवरण)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("detail_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { onBookmarkToggle(job) },
                        modifier = Modifier.testTag("detail_bookmark_button")
                    ) {
                        Icon(
                            imageVector = if (job.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (job.isBookmarked) SaffronAccent else Color.White
                        )
                    }

                    IconButton(
                        onClick = { shareJobDetails(context, job) },
                        modifier = Modifier.testTag("detail_share_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = HaryanaNavyPrimary
                )
            )
        },
        containerColor = PortalBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, PortalCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Surface(
                        color = HaryanaNavyContainer,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = job.organization,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            ),
                            color = HaryanaNavyDark,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = job.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            lineHeight = 24.sp
                        ),
                        color = Color(0xFF0F172A)
                    )

                    if (job.hindiTitle.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = job.hindiTitle,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp,
                                color = HaryanaNavyPrimary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = Color(0xFFF1F5F9))
                    Spacer(modifier = Modifier.height(12.dp))

                    // Brief Overview
                    Text(
                        text = job.briefDescription,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            color = Color(0xFF334155)
                        )
                    )

                    if (job.hindiDescription.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = job.hindiDescription,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 13.sp,
                                lineHeight = 19.sp,
                                color = Color(0xFF1E293B)
                            )
                        )
                    }
                }
            }

            // Quick Stats Grid: Vacancies, Last Date, Salary, Location
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatBox(
                    title = "Total Vacancies (पद)",
                    value = job.totalVacancies,
                    icon = Icons.Default.Group,
                    iconTint = HaryanaNavyPrimary,
                    modifier = Modifier.weight(1f)
                )
                StatBox(
                    title = "Last Date (अंतिम तिथि)",
                    value = job.lastDate,
                    icon = Icons.Default.CalendarMonth,
                    iconTint = AlertRed,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatBox(
                    title = "Salary / Pay Scale",
                    value = job.salary,
                    icon = Icons.Default.Payments,
                    iconTint = EmeraldGovtGreen,
                    modifier = Modifier.weight(1f)
                )
                StatBox(
                    title = "Job Location (स्थान)",
                    value = job.location,
                    icon = Icons.Default.LocationOn,
                    iconTint = SaffronAccent,
                    modifier = Modifier.weight(1f)
                )
            }

            // Important Dates Card
            DetailSectionCard(title = "📅 Important Dates (महत्वपूर्ण तिथियां)") {
                DetailRow(label = "Application Start (शुरू होने की तिथि):", value = job.startDate.ifEmpty { "Active Now" })
                DetailRow(label = "Application Last Date (अंतिम तिथि):", value = job.lastDate, highlightValue = true)
                DetailRow(label = "Exam Date / Admit Card:", value = job.examDate)
            }

            // Application Fee Card
            DetailSectionCard(title = "💳 Application Fees (आवेदन शुल्क)") {
                Text(
                    text = job.applicationFee,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF334155)
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "• Payment Mode: Online (Net Banking / Debit Card / UPI / CSC Wallet)",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )
                )
            }

            // Eligibility & Age Limit Card
            DetailSectionCard(title = "🎓 Eligibility & Age Limit (पात्रता व आयु सीमा)") {
                DetailRow(label = "Educational Qualification:", value = job.qualification)
                DetailRow(label = "Age Limit (आयु सीमा):", value = job.ageLimit)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "• आयु में नियमानुसार छूट (SC/BCA/BCB: 5 वर्ष की छूट नियमानुसार देय है।)",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )
                )
            }

            // Selection Process Card
            DetailSectionCard(title = "📋 Selection Process (चयन प्रक्रिया)") {
                Text(
                    text = job.selectionProcess,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = Color(0xFF1E293B)
                    )
                )
            }

            // Step-by-Step How to Apply (आवेदन कैसे करें)
            DetailSectionCard(title = "📝 How to Apply (आवेदन कैसे करें)") {
                val steps = listOf(
                    "सबसे पहले नीचे दिए गए आधिकारिक नोटिफिकेशन को ध्यानपूर्वक पढ़ें।",
                    "आवेदन करने के लिए 'Apply Online' बटन पर क्लिक करें।",
                    "हरियाणा निवासियों के लिए परिवार पहचान पत्र (PPP) दर्ज कर विवरण सत्यापित करें।",
                    "अपनी शैक्षणिक योग्यता, फोटो एवं हस्ताक्षर अपलोड करें।",
                    "ऑनलाइन माध्यम से आवेदन शुल्क का भुगतान करें।",
                    "आवेदन पत्र का फाइनल प्रिंट आउट भविष्य के संदर्भ के लिए सुरक्षित रख लें।"
                )

                steps.forEachIndexed { index, step ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            color = HaryanaNavyPrimary,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.size(20.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${index + 1}",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = step,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 13.sp,
                                color = Color(0xFF334155),
                                lineHeight = 18.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Official Important Links Card (CRITICAL)
            DetailSectionCard(title = "🔗 Useful Important Links (महत्वपूर्ण लिंक)") {
                OfficialLinkButton(
                    title = "Direct Apply Online (ऑनलाइन आवेदन करें)",
                    subtitle = "Click here to open portal application form",
                    icon = Icons.AutoMirrored.Filled.OpenInNew,
                    color = HaryanaNavyPrimary,
                    onClick = { openWebUrl(context, job.applyOnlineUrl) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OfficialLinkButton(
                    title = "Download Official Notification PDF (विज्ञप्ति)",
                    subtitle = "Read complete rules, syllabus & reservation details",
                    icon = Icons.Default.Download,
                    color = EmeraldGovtGreen,
                    onClick = { openWebUrl(context, job.officialNotificationUrl) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OfficialLinkButton(
                    title = "Official Commission Portal (आधिकारिक वेबसाइट)",
                    subtitle = job.officialWebsiteUrl,
                    icon = Icons.Default.Language,
                    color = SaffronAccent,
                    onClick = { openWebUrl(context, job.officialWebsiteUrl) }
                )
            }

            // Legal & Candidate Advisory
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                border = BorderStroke(1.dp, Color(0xFFFDE68A)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Advisory",
                        tint = Color(0xFFB45309),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "महत्वपूर्ण सलाह: यह जानकारी परीक्षार्थियों की सुविधा के लिए है। किसी भी पद पर आवेदन करने से पूर्व आधिकारिक विज्ञापन (Official Notification) को अवश्य जांच लें।",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            lineHeight = 17.sp,
                            color = Color(0xFF78350F)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun StatBox(
    title: String,
    value: String,
    icon: ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, PortalCardBorder)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 11.sp,
                    color = Color(0xFF64748B)
                )
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color(0xFF0F172A)
                )
            )
        }
    }
}

@Composable
fun DetailSectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, PortalCardBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF0F172A)
                )
            )
            HorizontalDivider(color = Color(0xFFF1F5F9))
            content()
        }
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String,
    highlightValue: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF64748B)
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 13.sp,
                fontWeight = if (highlightValue) FontWeight.Bold else FontWeight.SemiBold,
                color = if (highlightValue) AlertRed else Color(0xFF0F172A)
            )
        )
    }
}

@Composable
fun OfficialLinkButton(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.5.dp, color),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = color.copy(alpha = 0.05f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = color
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                        color = Color(0xFF64748B)
                    )
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

private fun openWebUrl(context: Context, url: String) {
    try {
        val safeUrl = if (url.startsWith("http://") || url.startsWith("https://")) url else "https://$url"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(safeUrl))
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Could not open link: $url", Toast.LENGTH_SHORT).show()
    }
}

private fun shareJobDetails(context: Context, job: JobItem) {
    val shareContent = "${job.title}\n${job.hindiTitle}\nVacancies: ${job.totalVacancies}\nLast Date: ${job.lastDate}\nQualification: ${job.qualification}\n\nApply Online: ${job.applyOnlineUrl}\n\nShared via Job Haryana App"
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareContent)
    }
    context.startActivity(Intent.createChooser(intent, "Share Job Notification"))
}
