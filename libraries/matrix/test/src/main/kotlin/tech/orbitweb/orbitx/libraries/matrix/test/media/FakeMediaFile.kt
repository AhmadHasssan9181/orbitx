/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.media

import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaFile
import java.io.File

class FakeMediaFile(private val path: String) : MediaFile {
    override fun path(): String {
        return path
    }

    override fun persist(path: String): Boolean {
        return File(path()).renameTo(File(path))
    }

    override fun close() = Unit
}
