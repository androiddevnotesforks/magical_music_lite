package com.magicalmusic.core_design

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = DarkPrimary,
    primaryVariant = Primary,
    secondary = Ascent,
    background = DarkPrimary,
    surface = DarkPrimary,
    onSurface = Color.White,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = Color(0xFFedf6f9),
    secondary = Ascent,
    background = Color.White,
    surface = Color.White,
    onSurface = DarkPrimary
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MargicalMusicAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors, typography = Typography, shapes = Shapes, content = content
    )
}

@Composable
fun isAppDarkTheme(viewModel: ThemeVm = hiltViewModel()): Boolean {
    val theme by viewModel.settingsUiState.collectAsState()
    return theme.isDarkMode
}