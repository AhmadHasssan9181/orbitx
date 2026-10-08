/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import com.bumble.appyx.core.plugin.plugins
import com.bumble.appyx.navmodel.backstack.BackStack
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.lockscreen.api.LockScreenEntryPoint
import tech.orbitweb.orbitx.features.lockscreen.impl.settings.LockScreenSettingsFlowNode
import tech.orbitweb.orbitx.features.lockscreen.impl.setup.LockScreenSetupFlowNode
import tech.orbitweb.orbitx.libraries.architecture.BackstackView
import tech.orbitweb.orbitx.libraries.architecture.BaseFlowNode
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.architecture.createNode
import tech.orbitweb.orbitx.libraries.di.SessionScope
import kotlinx.parcelize.Parcelize

@ContributesNode(SessionScope::class)
@AssistedInject
class LockScreenFlowNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
) : BaseFlowNode<LockScreenFlowNode.NavTarget>(
    backstack = BackStack(
        initialElement = plugins.filterIsInstance<Inputs>().first().initialNavTarget,
        savedStateMap = buildContext.savedStateMap,
    ),
    buildContext = buildContext,
    plugins = plugins,
) {
    data class Inputs(
        val initialNavTarget: NavTarget,
    ) : NodeInputs

    sealed interface NavTarget : Parcelable {
        @Parcelize
        data object Setup : NavTarget

        @Parcelize
        data object Settings : NavTarget
    }

    private class OnSetupDoneCallback(private val plugins: List<LockScreenEntryPoint.Callback>) : LockScreenSetupFlowNode.Callback {
        override fun onSetupDone() {
            plugins.forEach {
                it.onSetupDone()
            }
        }
    }

    override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            NavTarget.Setup -> {
                val callback = OnSetupDoneCallback(plugins())
                createNode<LockScreenSetupFlowNode>(buildContext, plugins = listOf(callback))
            }
            NavTarget.Settings -> {
                createNode<LockScreenSettingsFlowNode>(buildContext)
            }
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        BackstackView()
    }
}
