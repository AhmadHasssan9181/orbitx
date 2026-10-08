/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaupload.test

import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaOptimizationConfig
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaOptimizationConfigProvider
import tech.orbitweb.orbitx.libraries.preferences.api.store.VideoCompressionPreset

class FakeMediaOptimizationConfigProvider(
    val config: MediaOptimizationConfig = MediaOptimizationConfig(
        compressImages = true,
        videoCompressionPreset = VideoCompressionPreset.STANDARD,
    )
) : MediaOptimizationConfigProvider {
    override suspend fun get(): MediaOptimizationConfig = config
}
