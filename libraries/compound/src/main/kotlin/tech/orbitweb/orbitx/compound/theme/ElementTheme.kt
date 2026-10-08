/*
 * Copyright (c) 2026 Orbit Team / Orbit Commercial Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.compound.theme

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import tech.orbitweb.orbitx.compound.tokens.compoundTypography
import tech.orbitweb.orbitx.compound.tokens.generated.SemanticColors
import tech.orbitweb.orbitx.compound.tokens.generated.TypographyTokens
import tech.orbitweb.orbitx.compound.tokens.generated.compoundColorsDark
import tech.orbitweb.orbitx.compound.tokens.generated.compoundColorsLight

/**
 * Inspired from https://medium.com/@lucasyujideveloper/54cbcbde1ace
 */
object ElementTheme {
    /**
     * The current [SemanticColors] provided by [ElementTheme].
     * These come from Compound and are the recommended colors to use for custom components.
     * In Figma, these colors usually have the `Light/` or `Dark/` prefix.
     */
    val colors: SemanticColors
        @Composable
        @ReadOnlyComposable
        get() = LocalCompoundColors.current

    /**
     * The current Material 3 [ColorScheme] provided by [ElementTheme], coming from [MaterialTheme].
     * In Figma, these colors usually have the `M3/` prefix.
     */
    val materialColors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colorScheme

    /**
     * Compound [Typography] tokens. In Figma, these have the `Android/font/` prefix.
     */
    val typography: TypographyTokens = TypographyTokens

    /**
     * Returns whether the theme version used is the light or the dark one.
     */
    val isLightTheme: Boolean
        @Composable
        @ReadOnlyComposable
        get() = LocalCompoundColors.current.isLight
}

typealias OrbitTheme = ElementTheme

// Global variables (application level)
internal val LocalCompoundColors = staticCompositionLocalOf { compoundColorsLight }

/**
 * Sets up the theme for the application, or a part of it.
 *
 * @param theme the [Theme] to use. Defaults to [Theme.Dark] or [Theme.Light] based on the system setting.
 * @param applySystemBarsUpdate whether to update the system bars color scheme or not when the theme changes. It's `true` by default.
 * This is specially useful when you want to apply an alternate theme to a part of the app but don't want it to affect the system bars.
 * @param lightStatusBar whether to use a light status bar color scheme or not. By default, it's `true` for light themes and `false` for dark ones.
 * @param dynamicColor whether to enable MaterialYou or not. It's `false` by default.
 * @param compoundLight the [SemanticColors] to use in light theme.
 * @param compoundDark the [SemanticColors] to use in dark theme.
 * @param materialColorsLight the Material 3 [ColorScheme] to use in light theme.
 * @param materialColorsDark the Material 3 [ColorScheme] to use in dark theme.
 * @param typography the Material 3 [Typography] tokens to use. It'll use [compoundTypography] by default.
 * @param content the content to apply the theme to.
 */
@Composable
fun ElementTheme(
    theme: Theme = if (isSystemInDarkTheme()) Theme.Dark else Theme.Light,
    applySystemBarsUpdate: Boolean = true,
    lightStatusBar: Boolean = !theme.isDark(),
    // true to enable MaterialYou
    dynamicColor: Boolean = false,
    compoundLight: SemanticColors = compoundColorsLight,
    compoundDark: SemanticColors = compoundColorsDark,
    materialColorsLight: ColorScheme = compoundLight.toMaterialColorScheme(),
    materialColorsDark: ColorScheme = compoundDark.toMaterialColorScheme(),
    typography: Typography = compoundTypography,
    content: @Composable () -> Unit,
) {
    val darkTheme = theme.isDark()
    val orbitCosmicDark = compoundDark.copy(
        bgCanvasDefault = Color(0xFF0D0C1D),
        bgCanvasDefaultLevel1 = Color(0xFF16152B),
        bgAccentRest = Color(0xFF8B5CF6),
        bgAccentHovered = Color(0xFF7C3AED),
        bgAccentPressed = Color(0xFF6D28D9),
        bgAccentSelected = Color(0x338B5CF6),
        bgActionPrimaryRest = Color(0xFF8B5CF6),
        bgActionPrimaryHovered = Color(0xFF7C3AED),
        bgActionPrimaryPressed = Color(0xFF6D28D9),
        bgBadgeAccent = Color(0xFF8B5CF6),
        iconAccentPrimary = Color(0xFF8B5CF6),
        iconAccentTertiary = Color(0xFFA78BFA),
        borderAccentPrimary = Color(0xFF8B5CF6),
        borderAccentSubtle = Color(0xFF6366F1),
        textActionAccent = Color(0xFFA78BFA),
        textBadgeAccent = Color.White,
        bgDecorative1 = Color(0xFF6366F1),
        textDecorative1 = Color(0xFFE0E7FF),
    )
    val currentCompoundColor = when {
        darkTheme -> when (theme) {
            Theme.Black -> orbitCosmicDark.copy(bgCanvasDefault = Color.Black, bgCanvasDefaultLevel1 = Color(0xFF0D0D12))
            Theme.Ocean -> compoundDark.copy(
                bgCanvasDefault = Color(0xFF0F172A),
                bgCanvasDefaultLevel1 = Color(0xFF1E293B),
                bgAccentRest = Color(0xFF38BDF8),
                bgAccentHovered = Color(0xFF0284C7),
                bgAccentPressed = Color(0xFF0369A1),
                bgActionSecondaryRest = Color(0xFF1E293B),
            )
            Theme.Forest -> compoundDark.copy(
                bgCanvasDefault = Color(0xFF0F1E13),
                bgCanvasDefaultLevel1 = Color(0xFF172A1C),
                bgAccentRest = Color(0xFF4ADE80),
                bgAccentHovered = Color(0xFF22C55E),
                bgAccentPressed = Color(0xFF16A34A),
                bgActionSecondaryRest = Color(0xFF172A1C),
            )
            Theme.Sunset -> compoundDark.copy(
                bgCanvasDefault = Color(0xFF1A120E),
                bgCanvasDefaultLevel1 = Color(0xFF2B1C15),
                bgAccentRest = Color(0xFFFB923C),
                bgAccentHovered = Color(0xFFF97316),
                bgAccentPressed = Color(0xFFEA580C),
                bgActionSecondaryRest = Color(0xFF2B1C15),
            )
            Theme.Cyberpunk -> compoundDark.copy(
                bgCanvasDefault = Color(0xFF121015),
                bgCanvasDefaultLevel1 = Color(0xFF1E1A21),
                bgAccentRest = Color(0xFFF43F5E),
                bgAccentHovered = Color(0xFFE11D48),
                bgAccentPressed = Color(0xFFBE123C),
                bgActionSecondaryRest = Color(0xFF1E1A21),
            )
            Theme.Graphite -> compoundDark.copy(
                bgCanvasDefault = Color(0xFF15140F),
                bgCanvasDefaultLevel1 = Color(0xFF211F19),
                bgAccentRest = Color(0xFFC4A585),
                bgAccentHovered = Color(0xFFB08A62),
                bgAccentPressed = Color(0xFF96734E),
                bgActionSecondaryRest = Color(0xFF211F19),
            )
            Theme.Slate -> compoundDark.copy(
                bgCanvasDefault = Color(0xFF131417),
                bgCanvasDefaultLevel1 = Color(0xFF1E2025),
                bgAccentRest = Color(0xFFA7B8D0),
                bgAccentHovered = Color(0xFF8FA3BF),
                bgAccentPressed = Color(0xFF758CA9),
                bgActionSecondaryRest = Color(0xFF1E2025),
            )
            else -> orbitCosmicDark
        }
        else -> compoundLight
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> currentCompoundColor.toMaterialColorScheme()
    }

    val statusBarColorScheme = if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        if (lightStatusBar) {
            dynamicDarkColorScheme(context)
        } else {
            dynamicLightColorScheme(context)
        }
    } else {
        colorScheme
    }

    if (applySystemBarsUpdate) {
        val activity = LocalActivity.current as? ComponentActivity
        LaunchedEffect(statusBarColorScheme, theme, lightStatusBar) {
            activity?.enableEdgeToEdge(
                // For Status bar use the background color of the app
                statusBarStyle = SystemBarStyle.auto(
                    lightScrim = statusBarColorScheme.background.toArgb(),
                    darkScrim = statusBarColorScheme.background.toArgb(),
                    detectDarkMode = { !lightStatusBar }
                ),
                // For Navigation bar use a transparent color so the content can be seen through it
                navigationBarStyle = if (darkTheme) {
                    SystemBarStyle.dark(Color.Transparent.toArgb())
                } else {
                    SystemBarStyle.light(Color.Transparent.toArgb(), Color.Transparent.toArgb())
                }
            )
        }
    }
    CompositionLocalProvider(
        LocalCompoundColors provides currentCompoundColor,
        LocalContentColor provides colorScheme.onSurface,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}
