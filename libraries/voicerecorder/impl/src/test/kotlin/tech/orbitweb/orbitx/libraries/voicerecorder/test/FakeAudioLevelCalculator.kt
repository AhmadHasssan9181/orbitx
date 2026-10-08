/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.voicerecorder.test

import tech.orbitweb.orbitx.libraries.voicerecorder.impl.audio.AudioLevelCalculator
import kotlin.math.abs

class FakeAudioLevelCalculator : AudioLevelCalculator {
    override fun calculateAudioLevel(buffer: ShortArray): Float {
        return buffer.map { abs(it.toFloat()) }.average().toFloat() / Short.MAX_VALUE
    }
}
