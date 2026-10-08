/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.api.internal

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class MapTilerTileServerStyleUriBuilderTest {
    private val builder = MapTilerTileServerStyleUriBuilder(
        baseUrl = "https://base.url",
        apiKey = "anApiKey",
        lightMapId = "aLightMapId",
        darkMapId = "aDarkMapId",
    )

    @Test
    fun `light map uri`() {
        assertThat(
            builder.build(
                customMapStyleUrl = null,
                darkMode = false,
            )
        ).isEqualTo("https://base.url/aLightMapId/style.json?key=anApiKey")
    }

    @Test
    fun `dark map uri`() {
        assertThat(
            builder.build(
                customMapStyleUrl = null,
                darkMode = true,
            )
        ).isEqualTo("https://base.url/aDarkMapId/style.json?key=anApiKey")
    }

    @Test
    fun `custom map uri light`() {
        assertThat(
            builder.build(
                customMapStyleUrl = "https://custom.url/style.json",
                darkMode = false,
            )
        ).isEqualTo("https://custom.url/style.json?key=anApiKey")
    }

    @Test
    fun `custom map uri dark`() {
        assertThat(
            builder.build(
                customMapStyleUrl = "https://custom.url/style.json",
                darkMode = true,
            )
        ).isEqualTo("https://custom.url/style.json?key=anApiKey")
    }
}
