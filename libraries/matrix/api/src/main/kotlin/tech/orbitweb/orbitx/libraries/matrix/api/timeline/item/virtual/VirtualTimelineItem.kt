/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.virtual

import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline

sealed interface VirtualTimelineItem {
    data class DayDivider(
        val timestamp: Long
    ) : VirtualTimelineItem

    data object ReadMarker : VirtualTimelineItem

    data object RoomBeginning : VirtualTimelineItem

    data object LastForwardIndicator : VirtualTimelineItem

    data class LoadingIndicator(
        val direction: Timeline.PaginationDirection,
        val timestamp: Long,
    ) : VirtualTimelineItem

    data object TypingNotification : VirtualTimelineItem
}
