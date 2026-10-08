/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.viewfolder.impl.folder

import tech.orbitweb.orbitx.features.viewfolder.impl.model.Item
import kotlinx.collections.immutable.ImmutableList

data class ViewFolderState(
    val title: String,
    val content: ImmutableList<Item>,
)
