/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.usersearch.test

import tech.orbitweb.orbitx.libraries.usersearch.api.UserRepository
import tech.orbitweb.orbitx.libraries.usersearch.api.UserSearchResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeUserRepository : UserRepository {
    var providedQuery: String? = null
        private set

    private val flow = MutableSharedFlow<UserSearchResultState>()

    override fun search(query: String): Flow<UserSearchResultState> {
        providedQuery = query
        return flow
    }

    suspend fun emitState(state: UserSearchResultState) {
        flow.emit(state)
    }
}
