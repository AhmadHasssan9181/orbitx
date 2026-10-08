/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.timeline.item.virtual

import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.virtual.VirtualTimelineItem
import org.matrix.rustcomponents.sdk.VirtualTimelineItem as RustVirtualTimelineItem

class VirtualTimelineItemMapper {
    fun map(virtualTimelineItem: RustVirtualTimelineItem): VirtualTimelineItem {
        return when (virtualTimelineItem) {
            is RustVirtualTimelineItem.DateDivider -> VirtualTimelineItem.DayDivider(virtualTimelineItem.ts.toLong())
            RustVirtualTimelineItem.ReadMarker -> VirtualTimelineItem.ReadMarker
            RustVirtualTimelineItem.TimelineStart -> VirtualTimelineItem.RoomBeginning
        }
    }
}
