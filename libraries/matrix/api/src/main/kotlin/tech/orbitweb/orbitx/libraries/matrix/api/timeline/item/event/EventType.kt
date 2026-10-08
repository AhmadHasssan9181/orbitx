/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event

/**
 * Constants defining known event types from Matrix specifications.
 */
object EventType {
    const val MESSAGE = "m.room.message"

    // Call Events
    const val CALL_INVITE = "m.call.invite"

    const val RTC_NOTIFICATION = "org.matrix.msc4075.rtc.notification"
}
