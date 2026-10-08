/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.model.virtual

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class TimelineItemDaySeparatorModelProvider : PreviewParameterProvider<TimelineItemDaySeparatorModel> {
    override val values = sequenceOf(
        aTimelineItemDaySeparatorModel("Today"),
        aTimelineItemDaySeparatorModel("March 6, 2023")
    )
}

fun aTimelineItemDaySeparatorModel(formattedDate: String) = TimelineItemDaySeparatorModel(
    formattedDate = formattedDate
)
