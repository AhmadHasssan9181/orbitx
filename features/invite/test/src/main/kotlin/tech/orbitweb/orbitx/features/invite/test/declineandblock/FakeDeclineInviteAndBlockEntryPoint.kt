/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invite.test.declineandblock

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import tech.orbitweb.orbitx.features.invite.api.InviteData
import tech.orbitweb.orbitx.features.invite.api.declineandblock.DeclineInviteAndBlockEntryPoint
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeDeclineInviteAndBlockEntryPoint : DeclineInviteAndBlockEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        inviteData: InviteData,
    ): Node {
        lambdaError()
    }
}
