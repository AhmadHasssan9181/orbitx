/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.toolbox.test.systemclock

import tech.orbitweb.orbitx.services.toolbox.api.systemclock.SystemClock

const val A_FAKE_TIMESTAMP = 123L

class FakeSystemClock(
    var epochMillisResult: Long = A_FAKE_TIMESTAMP
) : SystemClock {
    override fun epochMillis() = epochMillisResult
}
