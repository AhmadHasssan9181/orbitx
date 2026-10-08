/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.voiceplayer.impl

import tech.orbitweb.orbitx.tests.testutils.simulateLongTask
import java.io.File

/**
 * A fake implementation of [VoiceMessageMediaRepo] for testing purposes.
 */
class FakeVoiceMessageMediaRepo : VoiceMessageMediaRepo {
    var shouldFail = false

    override suspend fun getMediaFile(): Result<File> = simulateLongTask {
        if (shouldFail) {
            Result.failure(IllegalStateException("Failed to get media file"))
        } else {
            Result.success(File(""))
        }
    }
}
