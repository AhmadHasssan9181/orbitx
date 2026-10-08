/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.startchat.api

import androidx.compose.runtime.MutableState
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

interface StartDMAction {
    /**
     * Try to find an existing DM with the given user, or create one if none exists.
     * @param matrixUser The user to start a DM with.
     * @param createIfDmDoesNotExist If true, create a DM if one does not exist. If false and the DM
     * does not exist, the action will fail with the value [ConfirmingStartDmWithMatrixUser].
     * @param actionState The state to update with the result of the action.
     */
    suspend fun execute(
        matrixUser: MatrixUser,
        createIfDmDoesNotExist: Boolean,
        actionState: MutableState<AsyncAction<RoomId>>,
    )
}
