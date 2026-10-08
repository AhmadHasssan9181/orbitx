/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.audio.api

enum class AudioFocusRequester {
    ElementCall,
    VoiceMessage,
    RecordVoiceMessage,
    MediaViewer,
}

interface AudioFocus {
    /**
     * Request audio focus for the given requester.
     * @param requester The mode for which to request audio focus.
     * @param onFocusLost Callback to be invoked when the audio focus is lost.
     * @return true if the audio focus was successfully requested, false otherwise.
     */
    fun requestAudioFocus(
        requester: AudioFocusRequester,
        onFocusLost: () -> Unit,
    )

    /**
     * Release the audio focus.
     */
    fun releaseAudioFocus()
}
