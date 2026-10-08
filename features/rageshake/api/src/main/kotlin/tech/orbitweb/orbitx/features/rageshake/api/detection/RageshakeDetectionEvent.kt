/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.api.detection

import tech.orbitweb.orbitx.features.rageshake.api.screenshot.ImageResult

sealed interface RageshakeDetectionEvent {
    data object Dismiss : RageshakeDetectionEvent
    data object Disable : RageshakeDetectionEvent
    data object StartDetection : RageshakeDetectionEvent
    data object StopDetection : RageshakeDetectionEvent
    data class ProcessScreenshot(val imageResult: ImageResult) : RageshakeDetectionEvent
}
