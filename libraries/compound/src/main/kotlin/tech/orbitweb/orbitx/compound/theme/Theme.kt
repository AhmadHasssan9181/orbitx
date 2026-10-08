/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.compound.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class Theme {
    System,
    Dark,
    Black,
    Light,
    Ocean,
    Forest,
    Sunset,
    Cyberpunk,
    Graphite,
    Slate,
}

private fun Theme.coerceBlackTheme(allowBlackTheme: Boolean): Theme {
    return if (this == Theme.Black && !allowBlackTheme) Theme.Dark else this
}

@Composable
fun Theme.isDark(): Boolean {
    return when (this) {
        Theme.System -> isSystemInDarkTheme()
        Theme.Dark,
        Theme.Black,
        Theme.Ocean,
        Theme.Forest,
        Theme.Sunset,
        Theme.Cyberpunk,
        Theme.Graphite,
        Theme.Slate -> true
        Theme.Light -> false
    }
}

fun Flow<String?>.mapToTheme(allowBlackTheme: Boolean): Flow<Theme> = map {
    when (it) {
        null -> Theme.System
        else -> try {
            Theme.valueOf(it)
        } catch (_: Exception) {
            Theme.System
        }
    }.coerceBlackTheme(allowBlackTheme)
}
