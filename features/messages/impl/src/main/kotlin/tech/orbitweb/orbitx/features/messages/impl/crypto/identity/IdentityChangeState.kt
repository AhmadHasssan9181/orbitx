/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.crypto.identity

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.libraries.matrix.ui.room.RoomMemberIdentityStateChange

data class IdentityChangeState(
    val roomMemberIdentityStateChanges: ImmutableList<RoomMemberIdentityStateChange>,
    val eventSink: (IdentityChangeEvent) -> Unit,
)
