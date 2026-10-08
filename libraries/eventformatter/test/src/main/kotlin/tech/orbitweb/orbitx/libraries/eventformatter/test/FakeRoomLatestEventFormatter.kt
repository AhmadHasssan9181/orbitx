/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.eventformatter.test

import tech.orbitweb.orbitx.libraries.eventformatter.api.RoomLatestEventFormatter
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.LatestEventValue

class FakeRoomLatestEventFormatter : RoomLatestEventFormatter {
    private var result: CharSequence? = null

    override fun format(latestEvent: LatestEventValue.Local, isDmRoom: Boolean): CharSequence? {
        return result
    }

    override fun format(latestEvent: LatestEventValue.Remote, isDmRoom: Boolean): CharSequence? {
        return result
    }

    fun givenFormatResult(result: CharSequence?) {
        this.result = result
    }
}
