/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.test

import android.content.Context
import android.content.Intent
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import tech.orbitweb.orbitx.features.lockscreen.api.LockScreenEntryPoint
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeLockScreenEntryPoint : LockScreenEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        navTarget: LockScreenEntryPoint.Target,
        callback: LockScreenEntryPoint.Callback,
    ): Node = lambdaError()

    override fun pinUnlockIntent(context: Context): Intent = lambdaError()
}
