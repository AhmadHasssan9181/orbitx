/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.voicerecorder.impl.audio

import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers

interface AudioReader {
    /**
     * Record audio data continuously.
     *
     * @param onAudio callback when audio is read.
     */
    suspend fun record(
        onAudio: suspend (Audio) -> Unit,
    )

    fun stop()

    interface Factory {
        fun create(config: AudioConfig, dispatchers: CoroutineDispatchers): AudioReader
    }
}
