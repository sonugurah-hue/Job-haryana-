package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkNavyPrimary,
    onPrimary = Color(0xFF0F172A),
    primaryContainer = HaryanaNavyDark,
    onPrimaryContainer = Color(0xFFDBEAFE),
    secondary = SaffronLight,
    onSecondary = Color(0xFF451A03),
    secondaryContainer = Color(0xFF78350F),
    onSecondaryContainer = Color(0xFFFEF3C7),
    tertiary = EmeraldLight,
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary
)

private val LightColorScheme = lightColorScheme(
    primary = HaryanaNavyPrimary,
    onPrimary = Color.White,
    primaryContainer = HaryanaNavyContainer,
    onPrimaryContainer = HaryanaNavyOnContainer,
    secondary = SaffronAccent,
    onSecondary = Color.White,
    secondaryContainer = SaffronContainer,
    onSecondaryContainer = SaffronOnContainer,
    tertiary = EmeraldGovtGreen,
    background = PortalBackground,
    surface = PortalSurface,
    onBackground = PortalTextPrimary,
    onSurface = PortalTextPrimary,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = PortalTextSecondary,
    outline = PortalCardBorder
)

@Composable
fun JobHaryanaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent portal branding
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Backwards-compatible alias for template
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) = JobHaryanaTheme(darkTheme, dynamicColor, content)
