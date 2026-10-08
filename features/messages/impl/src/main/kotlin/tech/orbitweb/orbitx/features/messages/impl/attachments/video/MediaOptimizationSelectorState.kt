/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.attachments.video

import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.preferences.api.store.VideoCompressionPreset
import kotlinx.collections.immutable.ImmutableList

data class MediaOptimizationSelectorState(
    // Adding an index helps to fix a Compose issue where the state of the wrong item is updated
    val index: Int,
    val maxUploadSize: AsyncData<Long>,
    val videoSizeEstimations: AsyncData<ImmutableList<VideoUploadEstimation>>,
    val isImageOptimizationEnabled: Boolean?,
    val selectedVideoPreset: VideoCompressionPreset?,
    val displayMediaSelectorViews: Boolean?,
    val displayVideoPresetSelectorDialog: Boolean,
    val eventSink: (MediaOptimizationSelectorEvent) -> Unit
)

data class VideoUploadEstimation(
    val preset: VideoCompressionPreset,
    val sizeInBytes: Long,
    val canUpload: Boolean,
)
