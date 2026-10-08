/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.viewfolder.impl.model

import androidx.compose.runtime.Immutable

@Immutable
sealed interface Item {
    data object Parent : Item

    data class Folder(
        val path: String,
        val name: String,
    ) : Item

    data class File(
        val path: String,
        val name: String,
        val formattedSize: String,
    ) : Item
}
