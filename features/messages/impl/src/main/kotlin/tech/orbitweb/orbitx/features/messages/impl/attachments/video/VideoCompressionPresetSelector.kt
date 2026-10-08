/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.attachments.video

import dev.zacsweers.metro.Inject
import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.preferences.api.store.VideoCompressionPreset

@Inject
class VideoCompressionPresetSelector {
    fun selectBestVideoPreset(
        expectedVideoPreset: VideoCompressionPreset,
        videoSizeEstimations: AsyncData<ImmutableList<VideoUploadEstimation>>,
    ): AsyncData<VideoCompressionPreset> {
        val estimations = videoSizeEstimations.dataOrNull() ?: return AsyncData.Loading()
        val bestEstimation = estimations.find { it.preset.ordinal >= expectedVideoPreset.ordinal && it.canUpload }?.preset
        return if (bestEstimation != null) {
            AsyncData.Success(bestEstimation)
        } else {
            AsyncData.Failure(
                IllegalStateException("No suitable video preset found for expected preset: $expectedVideoPreset")
            )
        }
    }
}
