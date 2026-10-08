/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invite.api.acceptdecline

import tech.orbitweb.orbitx.features.invite.api.InviteData

interface AcceptDeclineInviteEvents {
    data class AcceptInvite(val invite: InviteData) : AcceptDeclineInviteEvents
    data class DeclineInvite(val invite: InviteData, val blockUser: Boolean, val shouldConfirm: Boolean) : AcceptDeclineInviteEvents
}
