/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.viewfolder.test.file

import tech.orbitweb.orbitx.features.viewfolder.impl.file.FileContentReader

class FakeFileContentReader : FileContentReader {
    private var result: Result<List<String>> = Result.success(emptyList())

    fun givenResult(result: Result<List<String>>) {
        this.result = result
    }

    override suspend fun getLines(path: String): Result<List<String>> = result
}
