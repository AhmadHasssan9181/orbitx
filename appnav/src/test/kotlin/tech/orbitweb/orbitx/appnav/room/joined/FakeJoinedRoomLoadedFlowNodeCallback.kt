/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.appnav.room.joined

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkData
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeJoinedRoomLoadedFlowNodeCallback : JoinedRoomLoadedFlowNode.Callback {
    override fun onDone() = lambdaError()
    override fun navigateToRoom(roomId: RoomId, serverNames: List<String>, clearBackStack: Boolean) = lambdaError()
    override fun handlePermalinkClick(data: PermalinkData, pushToBackstack: Boolean) = lambdaError()
    override fun navigateToGlobalNotificationSettings() = lambdaError()
    override fun navigateToDeveloperSettings() = lambdaError()
}
