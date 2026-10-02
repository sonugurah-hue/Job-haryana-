package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldGovtGreen
import com.example.ui.theme.HaryanaNavyContainer
import com.example.ui.theme.HaryanaNavyDark
import com.example.ui.theme.HaryanaNavyPrimary
import com.example.ui.theme.PortalCardBorder
import com.example.ui.theme.SaffronAccent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NetlifyApiDialog(
    currentUrl: String,
    syncStatus: String,
    lastSyncTime: Long,
    isSyncing: Boolean,
    onUrlSave: (String) -> Unit,
    onResetDefault: () -> Unit,
    onSyncNow: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var inputUrl by remember(currentUrl) { mutableStateOf(currentUrl) }
    var showSampleCode by remember { mutableStateOf(false) }

    val formattedSyncTime = remember(lastSyncTime) {
        if (lastSyncTime > 0) {
            val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            sdf.format(Date(lastSyncTime))
        } else {
            "Never (कभी नहीं)"
        }
    }

    val sampleNetlifyFunctionCode = """// netlify/functions/jobs.js
exports.handler = async (event, context) => {
  return {
    statusCode: 200,
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*"
    },
    body: JSON.stringify({
      status: "success",
      lastUpdated: new Date().toISOString(),
      jobs: [
        {
          id: "hssc-cet-mains-2026",
          title: "HSSC CET Group C Mains Recruitment 2026",
          hindiTitle: "हरियाणा ग्रुप सी मेन्स भर्ती 2026",
          organization: "HSSC",
          category: "HARYANA_JOBS",
          totalVacancies: "7,575 Posts",
          lastDate: "28 Oct 2026",
          qualification: "12th / Graduate + CET",
          applyOnlineUrl: "https://onetimeregn.haryana.gov.in"
        }
      ]
    })
  };
};"""

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0xFF00C7B7).copy(alpha = 0.15f), // Netlify Teal
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.CloudSync,
                                contentDescription = "Netlify API",
                                tint = Color(0xFF007A70),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Netlify API Sync",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "नेटलिफ़ी लाइव जॉब ऑटो-अपडेट",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 11.sp,
                                color = Color(0xFF64748B)
                            )
                        )
                    }
                }

                Surface(
                    color = Color(0xFF00C7B7),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Netlify",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HorizontalDivider(color = Color(0xFFE2E8F0))

                Text(
                    text = "अपने Netlify फंक्शन या होस्ट किए गए JSON API का URL यहां दर्ज करें। ऐप नए विज्ञापनों को सीधे आपके Netlify सर्वर से सिंक करेगा।",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = Color(0xFF334155)
                    )
                )

                // API URL Input Box
                Column {
                    Text(
                        text = "Netlify API Endpoint URL:",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = HaryanaNavyDark
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = inputUrl,
                        onValueChange = {
                            inputUrl = it
                            onUrlSave(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("netlify_url_input"),
                        placeholder = {
                            Text("https://your-site.netlify.app/.netlify/functions/jobs", fontSize = 12.sp)
                        },
                        singleLine = false,
                        maxLines = 2,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HaryanaNavyPrimary,
                            unfocusedBorderColor = Color(0xFFCBD5E1),
                            focusedContainerColor = Color(0xFFF8FAFC),
                            unfocusedContainerColor = Color(0xFFF8FAFC)
                        )
                    )
                }

                // Sync Status Card
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (syncStatus.contains("Success")) EmeraldContainer else Color(0xFFF1F5F9)
                    ),
                    border = BorderStroke(1.dp, PortalCardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (syncStatus.contains("Success")) Icons.Default.CloudDone else Icons.Default.Cloud,
                                contentDescription = null,
                                tint = if (syncStatus.contains("Success")) EmeraldGovtGreen else HaryanaNavyPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Sync Status: $syncStatus",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (syncStatus.contains("Success")) EmeraldGovtGreen else Color(0xFF1E293B)
                                )
                            )
                        }
                        Text(
                            text = "Last Synced: $formattedSyncTime",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = Color(0xFF64748B)
                            )
                        )
                    }
                }

                // Quick Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            onResetDefault()
                            inputUrl = "https://job-haryana.netlify.app/.netlify/functions/jobs"
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Restore,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reset (रीसेट)", fontSize = 11.sp)
                    }

                    Button(
                        onClick = { onSyncNow(inputUrl) },
                        enabled = !isSyncing,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = HaryanaNavyPrimary),
                        modifier = Modifier
                            .weight(1.2f)
                            .testTag("netlify_sync_now_button")
                    ) {
                        if (isSyncing) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Syncing...", fontSize = 11.sp)
                        } else {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Sync Now (सिंक)", fontSize = 11.sp)
                        }
                    }
                }

                // Expandable Netlify Code & JSON Helper
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Code,
                                    contentDescription = null,
                                    tint = SaffronAccent,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Netlify Function Template (कोड देखें)",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                            IconButton(
                                onClick = { showSampleCode = !showSampleCode },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = if (showSampleCode) Icons.Default.Restore else Icons.Default.HelpOutline,
                                    contentDescription = "Toggle code",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        AnimatedVisibility(visible = showSampleCode) {
                            Column(modifier = Modifier.padding(top = 8.dp)) {
                                Text(
                                    text = "अपने Netlify प्रोजेक्ट में `netlify/functions/jobs.js` बनाएं:",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 10.sp,
                                        color = Color(0xFF475569)
                                    )
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                Surface(
                                    color = Color(0xFF1E293B),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(
                                            text = sampleNetlifyFunctionCode,
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 9.sp,
                                            lineHeight = 13.sp,
                                            color = Color(0xFFE2E8F0)
                                        )

                                        Spacer(modifier = Modifier.height(6.dp))

                                        OutlinedButton(
                                            onClick = {
                                                copyToClipboard(context, sampleNetlifyFunctionCode)
                                                Toast.makeText(context, "Netlify code copied to clipboard!", Toast.LENGTH_SHORT).show()
                                            },
                                            modifier = Modifier.align(Alignment.End),
                                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ContentCopy,
                                                contentDescription = null,
                                                modifier = Modifier.size(12.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Copy Code", fontSize = 10.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = HaryanaNavyPrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("netlify_dialog_close")
            ) {
                Text("Close (बंद करें)")
            }
        }
    )
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Netlify Jobs Function", text)
    clipboard.setPrimaryClip(clip)
}
