/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.voiceplayer.api

data class VoiceMessageState(
    val buttonType: ButtonType,
    val progress: Float,
    val time: String,
    val showCursor: Boolean,
    val playbackSpeed: Float,
    val eventSink: (event: VoiceMessageEvent) -> Unit,
) {
    enum class ButtonType {
        Play,
        Pause,
        Downloading,
        Retry,
        Disabled,
    }
}
