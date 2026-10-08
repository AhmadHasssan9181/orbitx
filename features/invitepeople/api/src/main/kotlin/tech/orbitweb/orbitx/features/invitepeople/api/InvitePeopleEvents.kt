/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invitepeople.api

interface InvitePeopleEvents {
    data object SendInvites : InvitePeopleEvents
    data object CloseSearch : InvitePeopleEvents
    data object ClearError : InvitePeopleEvents
}
