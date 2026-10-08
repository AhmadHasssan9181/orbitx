/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.voicerecorder.impl.audio

import java.io.File

interface Encoder {
    fun init(file: File)

    fun encode(buffer: ShortArray, readSize: Int)

    fun release()
}
