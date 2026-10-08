/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.eventformatter.api

import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.LatestEventValue

interface RoomLatestEventFormatter {
    fun format(latestEvent: LatestEventValue.Local, isDmRoom: Boolean): CharSequence?
    fun format(latestEvent: LatestEventValue.Remote, isDmRoom: Boolean): CharSequence?
}
