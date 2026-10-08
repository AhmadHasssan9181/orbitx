/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.model.event

import tech.orbitweb.orbitx.libraries.matrix.api.notification.CallIntent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventType

// State of the call, for now only isDeclined but in the future could be missed, active.
sealed interface RtcNotificationState {
    /** Some users have declined, byMe indicates if the current user is one of them. */
    data class Declined(val byMe: Boolean) : RtcNotificationState

    object Started : RtcNotificationState
}

class TimelineItemRtcNotificationContent(
    val callIntent: CallIntent,
    val state: RtcNotificationState,
) : TimelineItemEventContent {
    override val type: String = EventType.RTC_NOTIFICATION
}
