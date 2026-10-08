/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.timeline.postprocessor

import tech.orbitweb.orbitx.libraries.matrix.api.core.UniqueId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.MatrixTimelineItem
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.MembershipChange
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.OtherState
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.StateContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.virtual.VirtualTimelineItem
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID_2
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.aMessageContent
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.aProfileChangeMessageContent
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.anEventTimelineItem
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.item.event.aRoomMembershipContent

internal val timelineStartEvent = MatrixTimelineItem.Virtual(
    uniqueId = UniqueId("timeline_start"),
    virtual = VirtualTimelineItem.RoomBeginning,
)
internal val roomCreateEvent = MatrixTimelineItem.Event(
    uniqueId = UniqueId("m.room.create"),
    event = anEventTimelineItem(sender = A_USER_ID, content = StateContent("", OtherState.RoomCreate))
)
internal val roomCreatorJoinEvent = MatrixTimelineItem.Event(
    uniqueId = UniqueId("m.room.member"),
    event = anEventTimelineItem(content = aRoomMembershipContent(userId = A_USER_ID, change = MembershipChange.JOINED))
)
internal val otherMemberJoinEvent = MatrixTimelineItem.Event(
    uniqueId = UniqueId("m.room.member_other"),
    event = anEventTimelineItem(content = aRoomMembershipContent(userId = A_USER_ID_2, change = MembershipChange.JOINED))
)
internal val otherMemberLeaveEvent = MatrixTimelineItem.Event(
    uniqueId = UniqueId("m.room.member_leave"),
    event = anEventTimelineItem(content = aRoomMembershipContent(userId = A_USER_ID_2, change = MembershipChange.LEFT))
)
internal val profileChangeEvent = MatrixTimelineItem.Event(
    uniqueId = UniqueId("m.room.member_profile"),
    event = anEventTimelineItem(content = aProfileChangeMessageContent(displayName = "New Name", prevDisplayName = "Old Name"))
)
internal val messageEvent = MatrixTimelineItem.Event(
    uniqueId = UniqueId("m.room.message"),
    event = anEventTimelineItem(content = aMessageContent("hi"))
)
internal val messageEvent2 = MatrixTimelineItem.Event(
    uniqueId = UniqueId("m.room.message2"),
    event = anEventTimelineItem(content = aMessageContent("hello"))
)
internal val dayEvent = MatrixTimelineItem.Virtual(
    uniqueId = UniqueId("day"),
    virtual = VirtualTimelineItem.DayDivider(0),
)
