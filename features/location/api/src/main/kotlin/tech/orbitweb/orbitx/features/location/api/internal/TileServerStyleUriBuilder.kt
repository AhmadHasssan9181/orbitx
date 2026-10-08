/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.api.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import tech.orbitweb.orbitx.compound.theme.ElementTheme

/**
 * Builds a style URI for a MapLibre compatible tile server.
 *
 * Used for rendering dynamic maps.
 */
interface TileServerStyleUriBuilder {
    fun build(
        customMapStyleUrl: String?,
        darkMode: Boolean,
    ): String
}

/**
 * Provides and remembers a style URI for a MapLibre compatible tile server.
 *
 * Used for rendering dynamic maps.
 */
@Composable
fun rememberTileStyleUrl(
    customMapStyleUrl: String?,
): String {
    val darkMode = !ElementTheme.isLightTheme
    return remember(darkMode, customMapStyleUrl) {
        MapTilerTileServerStyleUriBuilder().build(
            customMapStyleUrl = customMapStyleUrl,
            darkMode = darkMode,
        )
    }
}
