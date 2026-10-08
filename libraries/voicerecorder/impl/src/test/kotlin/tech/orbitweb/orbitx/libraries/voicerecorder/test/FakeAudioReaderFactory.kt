/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.voicerecorder.test

import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.voicerecorder.impl.audio.Audio
import tech.orbitweb.orbitx.libraries.voicerecorder.impl.audio.AudioConfig
import tech.orbitweb.orbitx.libraries.voicerecorder.impl.audio.AudioReader

class FakeAudioReaderFactory(
    private val audio: List<Audio>
) : AudioReader.Factory {
    override fun create(config: AudioConfig, dispatchers: CoroutineDispatchers): AudioReader {
        return FakeAudioReader(dispatchers, audio)
    }
}
