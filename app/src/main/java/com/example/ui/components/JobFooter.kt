package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.NavCategory
import com.example.ui.theme.HaryanaNavyDark
import com.example.ui.theme.SaffronAccent
import com.example.ui.viewmodel.InfoDialogType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobFooter(
    onNavSelect: (NavCategory) -> Unit,
    onInfoDialogClick: (InfoDialogType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(HaryanaNavyDark)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo & Mission
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = null,
                tint = Color(0xFFFFD54F),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "JOB HARYANA (जॉब हरियाणा)",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                ),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "हरियाणा व केंद्र सरकार की सभी सरकारी नौकरियों, एडमिट कार्ड, परीक्षा परिणाम (Results) एवं कॉलेज प्रवेश की सबसे तेज व प्रामाणिक सूचना।",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                lineHeight = 17.sp,
                textAlign = TextAlign.Center
            ),
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = Color.White.copy(alpha = 0.15f))
        Spacer(modifier = Modifier.height(16.dp))

        // Important Policy and Info Links
        Text(
            text = "महत्वपूर्ण लिंक (Information & Policies)",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = SaffronAccent
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FooterLinkItem(
                title = "About Us (हमारे बारे में)",
                icon = Icons.Default.Info,
                onClick = { onInfoDialogClick(InfoDialogType.ABOUT_US) },
                testTag = "footer_about_us"
            )

            FooterLinkItem(
                title = "Contact Us (संपर्क)",
                icon = Icons.Default.Mail,
                onClick = { onInfoDialogClick(InfoDialogType.CONTACT_US) },
                testTag = "footer_contact_us"
            )

            FooterLinkItem(
                title = "Disclaimer (अस्वीकरण)",
                icon = Icons.Default.Security,
                onClick = { onInfoDialogClick(InfoDialogType.DISCLAIMER) },
                testTag = "footer_disclaimer"
            )

            FooterLinkItem(
                title = "Privacy Policy (गोपनीयता नीति)",
                icon = Icons.Default.Policy,
                onClick = { onInfoDialogClick(InfoDialogType.PRIVACY_POLICY) },
                testTag = "footer_privacy_policy"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Statutory Advisory Note
        Surface(
            color = Color.White.copy(alpha = 0.08f),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "अस्वीकरण (Disclaimer): Job Haryana एक स्वतंत्र करियर एवं शैक्षिक सूचना मंच है। इसका किसी भी सरकारी विभाग या आयोग से सीधा संबंध नहीं है। सभी उम्मीदवार आवेदन करने से पूर्व आधिकारिक विज्ञप्ति की पुष्टि अवश्य करें।",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 10.sp,
                    lineHeight = 14.sp,
                    textAlign = TextAlign.Center
                ),
                color = Color.White.copy(alpha = 0.65f),
                modifier = Modifier.padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "© 2026 Job Haryana Portal. All Rights Reserved.",
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp
            ),
            color = Color.White.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun FooterLinkItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        color = Color.White.copy(alpha = 0.12f),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFFFD54F),
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = Color.White
            )
        }
    }
}
