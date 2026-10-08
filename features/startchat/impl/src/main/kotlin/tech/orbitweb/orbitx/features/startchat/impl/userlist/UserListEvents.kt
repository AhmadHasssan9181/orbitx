/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.startchat.impl.userlist

import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

sealed interface UserListEvents {
    data class AddToSelection(val matrixUser: MatrixUser) : UserListEvents
    data class RemoveFromSelection(val matrixUser: MatrixUser) : UserListEvents
    data class OnSearchActiveChanged(val active: Boolean) : UserListEvents
}
