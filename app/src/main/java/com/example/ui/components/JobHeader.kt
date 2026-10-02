package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.NavCategory
import com.example.ui.theme.HaryanaNavyDark
import com.example.ui.theme.HaryanaNavyPrimary
import com.example.ui.theme.SaffronAccent
import com.example.ui.viewmodel.InfoDialogType

@Composable
fun JobHeader(
    bookmarkCount: Int,
    isRefreshing: Boolean,
    onRefreshClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onInfoClick: (InfoDialogType) -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        // Main Portal Top Bar with Navy Gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            HaryanaNavyDark,
                            HaryanaNavyPrimary
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Logo & Title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Job Haryana Emblem",
                            tint = Color(0xFFFFD54F),
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "JOB HARYANA",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.sp,
                                    fontSize = 20.sp
                                ),
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = SaffronAccent,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "LIVE",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = "जॉब हरियाणा • सरकारी नौकरी व रिजल्ट पोर्टल",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }

                // Actions: Saved Jobs, Sync Refresh, More Menu
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onBookmarkClick,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("saved_jobs_header_button")
                    ) {
                        BadgedBox(
                            badge = {
                                if (bookmarkCount > 0) {
                                    Badge(
                                        containerColor = SaffronAccent,
                                        contentColor = Color.White
                                    ) {
                                        Text(text = "$bookmarkCount", fontSize = 10.sp)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = "Saved Jobs",
                                tint = Color.White
                            )
                        }
                    }

                    IconButton(
                        onClick = onRefreshClick,
                        enabled = !isRefreshing,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("refresh_jobs_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Check for Updates",
                            tint = if (isRefreshing) Color.White.copy(alpha = 0.5f) else Color.White
                        )
                    }

                    Box {
                        IconButton(
                            onClick = { menuExpanded = true },
                            modifier = Modifier
                                .size(40.dp)
                                .testTag("header_menu_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Portal Info Menu",
                                tint = Color.White
                            )
                        }

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("About Us (हमारे बारे में)") },
                                onClick = {
                                    menuExpanded = false
                                    onInfoClick(InfoDialogType.ABOUT_US)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Contact Us (संपर्क करें)") },
                                onClick = {
                                    menuExpanded = false
                                    onInfoClick(InfoDialogType.CONTACT_US)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Disclaimer (अस्वीकरण)") },
                                onClick = {
                                    menuExpanded = false
                                    onInfoClick(InfoDialogType.DISCLAIMER)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Privacy Policy (गोपनीयता)") },
                                onClick = {
                                    menuExpanded = false
                                    onInfoClick(InfoDialogType.PRIVACY_POLICY)
                                }
                            )
                        }
                    }
                }
            }
        }

        // Live News Flash Ticker Bar
        NewsAlertTicker()
    }
}

@Composable
fun NewsAlertTicker() {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF8E1))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFE65100))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.NotificationsActive,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "अलर्ट",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📢 HSSC CET ग्रुप सी मेन्स आवेदन चालू  •  🔥 हरियाणा पुलिस 6,000 कांस्टेबल भर्ती नोटिफिकेशन  •  🎯 DHE कॉलेज यूजी/पीजी स्पॉट काउंसलिंग  •  ✅ रेलवे NTPC 11,558 पद ऑनलाइन फॉर्म",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = Color(0xFF5D4037)
            )
        }
    }
}
