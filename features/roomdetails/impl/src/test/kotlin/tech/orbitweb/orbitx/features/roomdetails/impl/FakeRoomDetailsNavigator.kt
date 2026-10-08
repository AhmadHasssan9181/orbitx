/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdetails.impl

import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeRoomDetailsNavigator(
    private val onDoneResult: () -> Unit = { lambdaError() }
) : RoomDetailsNavigator {
    override fun onDone() = onDoneResult()
}
