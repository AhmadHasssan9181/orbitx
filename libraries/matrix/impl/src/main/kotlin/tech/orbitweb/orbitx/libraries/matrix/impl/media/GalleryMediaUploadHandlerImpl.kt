/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.media

import org.matrix.rustcomponents.sdk.SendGalleryJoinHandle
import tech.orbitweb.orbitx.libraries.androidutils.file.safeDelete
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaUploadHandler
import java.io.File

class GalleryMediaUploadHandlerImpl(
    private val filesToUpload: List<File>,
    private val sendGalleryJoinHandle: SendGalleryJoinHandle,
) : MediaUploadHandler {
    override suspend fun await(): Result<Unit> =
        runCatchingExceptions {
            sendGalleryJoinHandle.join()
        }
            .also { cleanUpFiles() }

    override fun cancel() {
        sendGalleryJoinHandle.cancel()
        cleanUpFiles()
    }

    private fun cleanUpFiles() {
        filesToUpload.forEach { file -> file.safeDelete() }
    }
}
