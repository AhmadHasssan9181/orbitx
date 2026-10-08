/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.voicerecorder.test

import tech.orbitweb.orbitx.libraries.voicerecorder.impl.audio.Encoder
import java.io.File

class FakeEncoder(
    private val fakeFileSystem: FakeFileSystem
) : Encoder {
    private var curFile: File? = null
    override fun init(file: File) {
        curFile = file
    }

    override fun encode(buffer: ShortArray, readSize: Int) {
        val file = curFile
            ?: error("Encoder not initialized")

        fakeFileSystem.appendToFile(file, buffer, readSize)
    }

    override fun release() {
        curFile = null
    }
}
