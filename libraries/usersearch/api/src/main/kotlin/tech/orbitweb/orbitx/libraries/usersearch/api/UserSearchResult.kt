/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.usersearch.api

import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

data class UserSearchResult(
    val matrixUser: MatrixUser,
    val isUnresolved: Boolean = false,
)

data class UserSearchResultState(
    val results: List<UserSearchResult>,
    val isSearching: Boolean,
)
