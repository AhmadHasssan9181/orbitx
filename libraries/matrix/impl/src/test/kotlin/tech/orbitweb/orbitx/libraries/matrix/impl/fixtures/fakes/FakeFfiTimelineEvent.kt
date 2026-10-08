/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes

import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories.aRustTimelineEventContentMessageLike
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID_2
import tech.orbitweb.orbitx.services.toolbox.test.systemclock.A_FAKE_TIMESTAMP
import org.matrix.rustcomponents.sdk.NoHandle
import org.matrix.rustcomponents.sdk.TimelineEvent
import org.matrix.rustcomponents.sdk.TimelineEventContent

open class FakeFfiTimelineEvent(
    val timestamp: ULong = A_FAKE_TIMESTAMP.toULong(),
    val timelineEventContent: TimelineEventContent = aRustTimelineEventContentMessageLike(),
    val senderId: String = A_USER_ID_2.value,
) : TimelineEvent(NoHandle) {
    override fun timestamp(): ULong = timestamp
    override fun content(): TimelineEventContent = timelineEventContent
    override fun senderId(): String = senderId
}
