/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.paths

import java.io.File

data class SessionPaths(
    val fileDirectory: File,
    val cacheDirectory: File,
) {
    fun deleteRecursively() {
        fileDirectory.deleteRecursively()
        cacheDirectory.deleteRecursively()
    }
}
