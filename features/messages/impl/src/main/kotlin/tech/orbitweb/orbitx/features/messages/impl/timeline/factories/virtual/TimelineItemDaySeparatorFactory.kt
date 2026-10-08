/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.factories.virtual

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.virtual.TimelineItemDaySeparatorModel
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.virtual.TimelineItemVirtualModel
import tech.orbitweb.orbitx.libraries.dateformatter.api.DateFormatter
import tech.orbitweb.orbitx.libraries.dateformatter.api.DateFormatterMode
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.virtual.VirtualTimelineItem

@Inject
class TimelineItemDaySeparatorFactory(
    private val dateFormatter: DateFormatter,
) {
    fun create(virtualItem: VirtualTimelineItem.DayDivider): TimelineItemVirtualModel {
        val formattedDate = dateFormatter.format(
            timestamp = virtualItem.timestamp,
            mode = DateFormatterMode.Day,
            useRelative = true,
        )
        return TimelineItemDaySeparatorModel(
            formattedDate = formattedDate
        )
    }
}
