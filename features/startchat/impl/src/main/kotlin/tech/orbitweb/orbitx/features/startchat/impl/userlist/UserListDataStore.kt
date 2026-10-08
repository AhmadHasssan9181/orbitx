/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.startchat.impl.userlist

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Inject
class UserListDataStore {
    private val _selectedUsers: MutableStateFlow<List<MatrixUser>> = MutableStateFlow(emptyList())

    fun selectUser(user: MatrixUser) {
        if (!_selectedUsers.value.contains(user)) {
            _selectedUsers.tryEmit(_selectedUsers.value.plus(user))
        }
    }

    fun removeUserFromSelection(user: MatrixUser) {
        _selectedUsers.tryEmit(_selectedUsers.value.minus(user))
    }

    val selectedUsers = _selectedUsers.asStateFlow()
}
