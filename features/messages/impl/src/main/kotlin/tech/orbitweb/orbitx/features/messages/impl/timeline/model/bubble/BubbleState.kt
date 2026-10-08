/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.model.bubble

import tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineRoomInfo
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItemGroupPosition

data class BubbleState(
    val groupPosition: TimelineItemGroupPosition,
    val isMine: Boolean,
    val timelineRoomInfo: TimelineRoomInfo,
) {
    /** True to cut out the top start corner of the bubble, to give margin for the sender avatar. */
    val cutTopStart: Boolean = groupPosition.isNew() && !isMine && !timelineRoomInfo.isDm
}
