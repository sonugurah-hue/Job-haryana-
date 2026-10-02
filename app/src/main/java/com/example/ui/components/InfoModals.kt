package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.HaryanaNavyDark
import com.example.ui.theme.HaryanaNavyPrimary
import com.example.ui.theme.SaffronAccent
import com.example.ui.viewmodel.InfoDialogType

@Composable
fun InfoDialog(
    type: InfoDialogType,
    onDismiss: () -> Unit
) {
    val title = when (type) {
        InfoDialogType.ABOUT_US -> "About Us (हमारे बारे में)"
        InfoDialogType.CONTACT_US -> "Contact Us (संपर्क करें)"
        InfoDialogType.DISCLAIMER -> "Disclaimer (अस्वीकरण व नीति)"
        InfoDialogType.PRIVACY_POLICY -> "Privacy Policy (गोपनीयता नीति)"
    }

    val icon = when (type) {
        InfoDialogType.ABOUT_US -> Icons.Default.Info
        InfoDialogType.CONTACT_US -> Icons.Default.Email
        InfoDialogType.DISCLAIMER -> Icons.Default.Security
        InfoDialogType.PRIVACY_POLICY -> Icons.Default.Policy
    }

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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = HaryanaNavyPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        color = Color(0xFF0F172A)
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Gray
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                HorizontalDivider(color = Color(0xFFE2E8F0))

                when (type) {
                    InfoDialogType.ABOUT_US -> {
                        Text(
                            text = "Job Haryana (जॉब हरियाणा) हरियाणा और उत्तर भारत के लाखों युवाओं को समर्पित एक विश्वसनीय रोजगार व शैक्षिक सूचना पोर्टल है।",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 13.sp,
                                lineHeight = 19.sp,
                                color = Color(0xFF1E293B)
                            )
                        )
                        Text(
                            text = "हमारा मुख्य उद्देश्य हरियाणा कर्मचारी चयन आयोग (HSSC), हरियाणा लोक सेवा आयोग (HPSC), हरियाणा कौशल रोजगार निगम (HKRN), केंद्रीय एसएससी, रेलवे, बैंकिंग तथा राज्य विश्वविद्यालयों (KUK, MDU, CDLU) के नवीनतम फॉर्म, एडमिट कार्ड, परीक्षा परिणाम व उत्तर कुंजियों की सटीक जानकारी हिंदी एवं अंग्रेजी में त्वरित रूप से प्रदान करना है।",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                color = Color(0xFF475569)
                            )
                        )
                    }

                    InfoDialogType.CONTACT_US -> {
                        Text(
                            text = "यदि आपके पास किसी भर्ती, परिणाम या तकनीकी सुधार के संबंध में कोई सुझाव या प्रश्न है, तो आप हमारी सहायता टीम से संपर्क कर सकते हैं:",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                                color = Color(0xFF1E293B)
                            )
                        )

                        Surface(
                            color = Color(0xFFF8FAFC),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = null,
                                        tint = HaryanaNavyPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Email: support@jobharyana.info",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 12.sp
                                        )
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = SaffronAccent,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Location: Sector 14, Panchkula & Rohtak, Haryana",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontSize = 12.sp
                                        )
                                    )
                                }

                                Text(
                                    text = "कार्य समय: सोमवार से शनिवार (प्रातः 9:00 बजे से सायं 6:00 बजे तक)",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 11.sp,
                                        color = Color(0xFF64748B)
                                    )
                                )
                            }
                        }
                    }

                    InfoDialogType.DISCLAIMER -> {
                        Text(
                            text = "कानूनी अस्वीकरण (Legal Disclaimer):",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB45309)
                            )
                        )
                        Text(
                            text = "1. 'Job Haryana' कोई सरकारी संस्था अथवा सरकारी विभाग का ऐप नहीं है। यह विशुद्ध रूप से छात्रों व बेरोजगार युवाओं को सार्वजनिक रूप से उपलब्ध रोजगार विज्ञापनों की जानकारी सुलभ कराने के उद्देश्य से संचालित है।",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                                color = Color(0xFF334155)
                            )
                        )
                        Text(
                            text = "2. हम सभी आधिकारिक भर्ती विज्ञापनों और आयोगों के गजट की पुष्टि के बाद ही जानकारी प्रकाशित करते हैं, फिर भी उम्मीदवारों को अंतिम निर्णय लेने या शुल्क भुगतान करने से पहले आधिकारिक वेबसाइट की पुष्टि करने का परामर्श दिया जाता है।",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                                color = Color(0xFF334155)
                            )
                        )
                    }

                    InfoDialogType.PRIVACY_POLICY -> {
                        Text(
                            text = "गोपनीयता नीति (Privacy Policy):",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = HaryanaNavyPrimary
                            )
                        )
                        Text(
                            text = "1. Job Haryana उपयोगकर्ता की व्यक्तिगत गोपनीय जानकारी (जैसे पासवर्ड, बैंक विवरण या आधार) एकत्रित नहीं करता है।",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                                color = Color(0xFF334155)
                            )
                        )
                        Text(
                            text = "2. बुकमार्क की गई नौकरियां (Saved Jobs) केवल आपके स्थानीय डिवाइस (Local Room Database) पर सुरक्षित रहती हैं।",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                                color = Color(0xFF334155)
                            )
                        )
                        Text(
                            text = "3. ऐप में प्रयुक्त सभी लिंक सीधे संबंधित विभाग की आधिकारिक वेबसाइट (उदा. hssc.gov.in, ssc.gov.in) पर रिडायरेक्ट करते हैं।",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                                color = Color(0xFF334155)
                            )
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = HaryanaNavyPrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("info_dialog_ok_button")
            ) {
                Text("ठीक है (Got It)")
            }
        }
    )
}
