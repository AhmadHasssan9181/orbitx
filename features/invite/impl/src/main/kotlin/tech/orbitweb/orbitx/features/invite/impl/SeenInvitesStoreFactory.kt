/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invite.impl

import kotlinx.coroutines.CoroutineScope
import tech.orbitweb.orbitx.features.invite.api.SeenInvitesStore
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

interface SeenInvitesStoreFactory {
    fun getOrCreate(
        sessionId: SessionId,
        sessionCoroutineScope: CoroutineScope,
    ): SeenInvitesStore
}
