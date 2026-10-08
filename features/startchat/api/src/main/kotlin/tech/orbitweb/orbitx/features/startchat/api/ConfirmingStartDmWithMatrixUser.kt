/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.startchat.api

import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

data class ConfirmingStartDmWithMatrixUser(
    val matrixUser: MatrixUser,
    val isUserIdentityUnknown: Boolean,
) : AsyncAction.Confirming
