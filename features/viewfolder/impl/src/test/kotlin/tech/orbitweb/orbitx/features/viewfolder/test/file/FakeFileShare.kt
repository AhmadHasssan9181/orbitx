/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.viewfolder.test.file

import tech.orbitweb.orbitx.features.viewfolder.impl.file.FileShare

class FakeFileShare : FileShare {
    var hasBeenCalled = false
        private set

    override suspend fun share(path: String) {
        hasBeenCalled = true
    }
}
