/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.viewfolder.impl.folder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.features.viewfolder.impl.model.Item
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta

@AssistedInject
class ViewFolderPresenter(
    @Assisted val canGoUp: Boolean,
    @Assisted val path: String,
    private val folderExplorer: FolderExplorer,
    private val buildMeta: BuildMeta,
) : Presenter<ViewFolderState> {
    @AssistedFactory
    interface Factory {
        fun create(canGoUp: Boolean, path: String): ViewFolderPresenter
    }

    @Composable
    override fun present(): ViewFolderState {
        var content by remember { mutableStateOf<ImmutableList<Item>>(persistentListOf()) }
        val title = remember {
            buildString {
                if (path.contains(buildMeta.applicationId)) {
                    append("…")
                }
                append(path.substringAfter(buildMeta.applicationId))
            }
        }
        LaunchedEffect(Unit) {
            content = buildList {
                if (canGoUp) add(Item.Parent)
                addAll(folderExplorer.getItems(path))
            }.toImmutableList()
        }
        return ViewFolderState(
            title = title,
            content = content,
        )
    }
}
