/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.voiceplayer.api

sealed interface VoiceMessageEvent {
    data object PlayPause : VoiceMessageEvent
    data class Seek(val percentage: Float) : VoiceMessageEvent
    data object ChangePlaybackSpeed : VoiceMessageEvent
}
