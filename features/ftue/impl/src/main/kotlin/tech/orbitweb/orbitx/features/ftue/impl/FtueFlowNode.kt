/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.ftue.impl

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.newRoot
import com.bumble.appyx.navmodel.backstack.operation.replace
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.analytics.api.AnalyticsEntryPoint
import tech.orbitweb.orbitx.features.ftue.impl.notifications.NotificationsOptInNode
import tech.orbitweb.orbitx.features.ftue.impl.sessionverification.FtueSessionVerificationFlowNode
import tech.orbitweb.orbitx.features.ftue.impl.state.DefaultFtueService
import tech.orbitweb.orbitx.features.ftue.impl.state.FtueStep
import tech.orbitweb.orbitx.features.ftue.impl.state.InternalFtueState
import tech.orbitweb.orbitx.features.lockscreen.api.LockScreenEntryPoint
import tech.orbitweb.orbitx.libraries.architecture.BackstackView
import tech.orbitweb.orbitx.libraries.architecture.BaseFlowNode
import tech.orbitweb.orbitx.libraries.architecture.createNode
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.ui.common.nodes.emptyNode

@ContributesNode(SessionScope::class)
@AssistedInject
class FtueFlowNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    private val defaultFtueService: DefaultFtueService,
    private val analyticsEntryPoint: AnalyticsEntryPoint,
    private val lockScreenEntryPoint: LockScreenEntryPoint,
) : BaseFlowNode<FtueFlowNode.NavTarget>(
    backstack = BackStack(
        initialElement = NavTarget.Placeholder,
        savedStateMap = buildContext.savedStateMap,
    ),
    buildContext = buildContext,
    plugins = plugins,
) {
    sealed interface NavTarget : Parcelable {
        @Parcelize
        data object Placeholder : NavTarget

        @Parcelize
        data object SessionVerification : NavTarget

        @Parcelize
        data object NotificationsOptIn : NavTarget

        @Parcelize
        data object AnalyticsOptIn : NavTarget

        @Parcelize
        data object LockScreenSetup : NavTarget
    }

    override fun onBuilt() {
        super.onBuilt()
        defaultFtueService.ftueStepStateFlow
            .filterIsInstance(InternalFtueState.Incomplete::class)
            .onEach {
                showStep(it.nextStep)
            }
            .launchIn(lifecycleScope)
    }

    override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            NavTarget.Placeholder -> {
                emptyNode(buildContext)
            }
            is NavTarget.SessionVerification -> {
                val callback = object : FtueSessionVerificationFlowNode.Callback {
                    override fun onDone() {
                        defaultFtueService.onUserCompletedSessionVerification()
                    }
                }
                createNode<FtueSessionVerificationFlowNode>(buildContext, listOf(callback))
            }
            NavTarget.NotificationsOptIn -> {
                val callback = object : NotificationsOptInNode.Callback {
                    override fun onNotificationsOptInFinished() {
                        defaultFtueService.updateFtueStep()
                    }
                }
                createNode<NotificationsOptInNode>(buildContext, listOf(callback))
            }
            NavTarget.AnalyticsOptIn -> {
                analyticsEntryPoint.createNode(this, buildContext)
            }
            NavTarget.LockScreenSetup -> {
                val callback = object : LockScreenEntryPoint.Callback {
                    override fun onSetupDone() {
                        defaultFtueService.updateFtueStep()
                    }
                }
                lockScreenEntryPoint.createNode(
                    parentNode = this,
                    buildContext = buildContext,
                    navTarget = LockScreenEntryPoint.Target.Setup,
                    callback = callback,
                )
            }
        }
    }

    private fun showStep(ftueStep: FtueStep) {
        when (ftueStep) {
            FtueStep.WaitingForInitialState -> {
                backstack.newRoot(NavTarget.Placeholder)
            }
            FtueStep.SessionVerification -> {
                backstack.newRoot(NavTarget.SessionVerification)
            }
            FtueStep.NotificationsOptIn -> {
                backstack.newRoot(NavTarget.NotificationsOptIn)
            }
            FtueStep.AnalyticsOptIn -> {
                backstack.replace(NavTarget.AnalyticsOptIn)
            }
            FtueStep.LockscreenSetup -> {
                backstack.newRoot(NavTarget.LockScreenSetup)
            }
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        BackstackView()
    }
}
