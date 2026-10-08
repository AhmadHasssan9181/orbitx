/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invite.impl.acceptdecline

import tech.orbitweb.orbitx.features.invite.api.acceptdecline.AcceptDeclineInviteEvents

sealed interface InternalAcceptDeclineInviteEvents : AcceptDeclineInviteEvents {
    data object ClearAcceptActionState : InternalAcceptDeclineInviteEvents
    data object ClearDeclineActionState : InternalAcceptDeclineInviteEvents
}
