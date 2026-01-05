package com.example.dot__simple_habit_tracker.ui.theme

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

val LightColorScheme = lightColorScheme(
    primary = HunterGreen,
    onPrimary = Color.White,
    secondary = StormGrey,
    onSecondary = Color.White,
    tertiary = Rust,
    onTertiary = Color.White,
    background = Mist,
    onBackground = Color(0xFF191C1A),
    surface = Color.White,
    onSurface = Color(0xFF191C1A)
)

val DarkColorScheme = darkColorScheme(
    primary = PaleMoss,
    onPrimary = Color(0xFF0F1510),
    secondary = Fog,
    onSecondary = Color(0xFF1A2229),
    tertiary = FadedRust,
    onTertiary = Color(0xFF2B1614),
    background = DeepNight,
    onBackground = Color(0xFFE2E3DD),
    surface = Gunmetal,
    onSurface = Color(0xFFE2E3DD)
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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