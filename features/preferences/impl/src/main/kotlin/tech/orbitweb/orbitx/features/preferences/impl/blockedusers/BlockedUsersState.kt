/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.blockedusers

import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import kotlinx.collections.immutable.ImmutableList

data class BlockedUsersState(
    val blockedUsers: ImmutableList<MatrixUser>,
    val unblockUserAction: AsyncAction<Unit>,
    val eventSink: (BlockedUsersEvents) -> Unit,
)
