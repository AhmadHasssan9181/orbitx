/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.voicerecorder.test

import tech.orbitweb.orbitx.libraries.voicerecorder.impl.file.VoiceFileConfig
import tech.orbitweb.orbitx.libraries.voicerecorder.impl.file.VoiceFileManager
import java.io.File

class FakeVoiceFileManager(
    private val fakeFileSystem: FakeFileSystem,
    private val config: VoiceFileConfig,
    private val fileId: String,
) : VoiceFileManager {
    override fun createFile(): File {
        val file = File("${config.cacheSubdir}/$fileId.${config.fileExt}")
        fakeFileSystem.createFile(file)
        return file
    }

    override fun deleteFile(file: File) {
        fakeFileSystem.deleteFile(file)
    }
}
