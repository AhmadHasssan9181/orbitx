/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.viewfolder.test.folder

import tech.orbitweb.orbitx.features.viewfolder.impl.folder.FolderExplorer
import tech.orbitweb.orbitx.features.viewfolder.impl.model.Item

class FakeFolderExplorer : FolderExplorer {
    private var result: List<Item> = emptyList()

    fun givenResult(result: List<Item>) {
        this.result = result
    }

    override suspend fun getItems(path: String): List<Item> = result
}
