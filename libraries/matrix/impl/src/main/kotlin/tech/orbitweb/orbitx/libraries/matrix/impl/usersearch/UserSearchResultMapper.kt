/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.usersearch

import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixSearchUserResults
import tech.orbitweb.orbitx.libraries.matrix.impl.mapper.map
import kotlinx.collections.immutable.toImmutableList
import org.matrix.rustcomponents.sdk.SearchUsersResults

object UserSearchResultMapper {
    fun map(result: SearchUsersResults): MatrixSearchUserResults {
        return MatrixSearchUserResults(
            results = result.results
                .map { userProfile -> userProfile.map() }
                .toImmutableList(),
            limited = result.limited,
        )
    }
}
