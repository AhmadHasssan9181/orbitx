/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.media

import tech.orbitweb.orbitx.libraries.matrix.api.media.MatrixMediaLoader
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaFile
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeMatrixMediaLoader : MatrixMediaLoader {
    var shouldFail = false
    var path: String = ""

    override suspend fun loadMediaContent(source: MediaSource): Result<ByteArray> = simulateLongTask {
        if (shouldFail) {
            Result.failure(RuntimeException())
        } else {
            Result.success(ByteArray(0))
        }
    }

    override suspend fun loadMediaThumbnail(source: MediaSource, width: Long, height: Long): Result<ByteArray> = simulateLongTask {
        if (shouldFail) {
            Result.failure(RuntimeException())
        } else {
            Result.success(ByteArray(0))
        }
    }

    override suspend fun downloadMediaFile(
        source: MediaSource,
        mimeType: String?,
        filename: String?,
        useCache: Boolean,
    ): Result<MediaFile> = simulateLongTask {
        if (shouldFail) {
            Result.failure(RuntimeException())
        } else {
            Result.success(FakeMediaFile(path))
        }
    }
}
