/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.viewfolder.impl.folder

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.features.viewfolder.impl.model.Item

data class ViewFolderState(
    val title: String,
    val content: ImmutableList<Item>,
)
