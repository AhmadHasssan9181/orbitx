/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.media

import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaFile
import org.matrix.rustcomponents.sdk.MediaFileHandle

class RustMediaFile(private val inner: MediaFileHandle) : MediaFile {
    override fun path(): String {
        return inner.path()
    }

    override fun persist(path: String): Boolean {
        return inner.persist(path)
    }

    override fun close() {
        inner.close()
    }
}
