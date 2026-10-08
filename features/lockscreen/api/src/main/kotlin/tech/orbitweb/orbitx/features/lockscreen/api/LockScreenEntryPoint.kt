/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.api

import android.content.Context
import android.content.Intent
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import tech.orbitweb.orbitx.libraries.architecture.FeatureEntryPoint

interface LockScreenEntryPoint : FeatureEntryPoint {
    fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        navTarget: Target,
        callback: Callback,
    ): Node

    fun pinUnlockIntent(context: Context): Intent

    interface Callback : Plugin {
        fun onSetupDone()
    }

    enum class Target {
        Settings,
        Setup,
    }
}
