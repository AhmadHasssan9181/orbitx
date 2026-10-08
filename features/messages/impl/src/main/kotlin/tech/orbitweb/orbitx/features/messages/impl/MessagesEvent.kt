/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl

import tech.orbitweb.orbitx.features.messages.impl.actionlist.model.TimelineItemAction
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventOrTransactionId
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

sealed interface MessagesEvent {
    data class HandleAction(val action: TimelineItemAction, val event: TimelineItem.Event) : MessagesEvent
    data class ToggleReaction(val emoji: String, val eventOrTransactionId: EventOrTransactionId) : MessagesEvent
    data class InviteDialogDismissed(val action: InviteDialogAction) : MessagesEvent
    data class OnUserClicked(val user: MatrixUser) : MessagesEvent
    data object StopLiveLocationShare : MessagesEvent
    data object ShowLiveLocationShare : MessagesEvent
    data object MarkAsFullyReadAndExit : MessagesEvent
}

enum class InviteDialogAction {
    Cancel,
    Invite,
}
