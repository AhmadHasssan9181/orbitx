/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.voicerecorder.test

import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.voicerecorder.impl.audio.Audio
import tech.orbitweb.orbitx.libraries.voicerecorder.impl.audio.AudioReader
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

class FakeAudioReader(
    private val dispatchers: CoroutineDispatchers,
    private val audio: List<Audio>,
) : AudioReader {
    private var isRecording = false
    override suspend fun record(onAudio: suspend (Audio) -> Unit) {
        isRecording = true
        withContext(dispatchers.io) {
            val audios = audio.iterator()
            while (audios.hasNext()) {
                if (!isRecording) break
                onAudio(audios.next())
                yield()
            }
            while (isActive) {
                // do not return from the coroutine until it is cancelled
                yield()
            }
        }
    }

    override fun stop() {
        isRecording = false
    }
}
