/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.troubleshoot.impl.history

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

sealed interface PushHistoryEvents {
    data class SetShowOnlyErrors(val showOnlyErrors: Boolean) : PushHistoryEvents
    data class Reset(val requiresConfirmation: Boolean) : PushHistoryEvents
    data class NavigateTo(val sessionId: SessionId, val roomId: RoomId, val eventId: EventId) : PushHistoryEvents
    data object ClearDialog : PushHistoryEvents
}
