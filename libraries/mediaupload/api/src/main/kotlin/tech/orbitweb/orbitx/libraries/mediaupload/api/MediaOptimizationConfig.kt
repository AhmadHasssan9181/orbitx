/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaupload.api

import tech.orbitweb.orbitx.libraries.androidutils.media.VideoCompressorHelper
import tech.orbitweb.orbitx.libraries.preferences.api.store.VideoCompressionPreset

data class MediaOptimizationConfig(
    val compressImages: Boolean,
    val videoCompressionPreset: VideoCompressionPreset,
)

fun VideoCompressionPreset.compressorHelper(): VideoCompressorHelper = when (this) {
    VideoCompressionPreset.STANDARD -> VideoCompressorHelper(1280)
    VideoCompressionPreset.HIGH -> VideoCompressorHelper(1920)
    VideoCompressionPreset.LOW -> VideoCompressorHelper(640)
}
