/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.datasource

import tech.orbitweb.orbitx.libraries.dateformatter.api.DateFormatter
import tech.orbitweb.orbitx.libraries.dateformatter.test.FakeDateFormatter
import tech.orbitweb.orbitx.libraries.eventformatter.api.RoomLatestEventFormatter
import tech.orbitweb.orbitx.libraries.eventformatter.test.FakeRoomLatestEventFormatter

fun aRoomListRoomSummaryFactory(
    dateFormatter: DateFormatter = FakeDateFormatter { _, _, _ -> "Today" },
    roomLatestEventFormatter: RoomLatestEventFormatter = FakeRoomLatestEventFormatter(),
) = RoomListRoomSummaryFactory(
    dateFormatter = dateFormatter,
    roomLatestEventFormatter = roomLatestEventFormatter,
)
