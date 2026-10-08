/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.usersearch.test

import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.libraries.usersearch.api.UserListDataSource

class FakeUserListDataSource : UserListDataSource {
    private var searchResult: List<MatrixUser> = emptyList()
    private var profile: MatrixUser? = null

    override suspend fun search(query: String, count: Long): List<MatrixUser> = searchResult.take(count.toInt())

    override suspend fun getProfile(userId: UserId): MatrixUser? = profile

    fun givenSearchResult(users: List<MatrixUser>) {
        this.searchResult = users
    }

    fun givenUserProfile(matrixUser: MatrixUser?) {
        this.profile = matrixUser
    }
}
