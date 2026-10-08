/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:JvmName("TileServerStyleUriBuilderKt")

package tech.orbitweb.orbitx.features.location.api.internal

import tech.orbitweb.orbitx.features.location.api.BuildConfig

internal class MapTilerTileServerStyleUriBuilder(
    private val baseUrl: String,
    private val apiKey: String,
    private val lightMapId: String,
    private val darkMapId: String,
) : TileServerStyleUriBuilder {
    constructor() : this(
        baseUrl = BuildConfig.MAPTILER_BASE_URL.removeSuffix("/"),
        apiKey = BuildConfig.MAPTILER_API_KEY,
        lightMapId = BuildConfig.MAPTILER_LIGHT_MAP_ID,
        darkMapId = BuildConfig.MAPTILER_DARK_MAP_ID,
    )

    override fun build(
        customMapStyleUrl: String?,
        darkMode: Boolean,
    ): String {
        return buildString {
            if (customMapStyleUrl.isNullOrBlank()) {
                val mapId = if (darkMode) darkMapId else lightMapId
                append("$baseUrl/$mapId/style.json")
            } else {
                append(customMapStyleUrl)
            }
            append("?key=$apiKey")
        }
    }
}
