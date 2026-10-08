/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.setup

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.lifecycle.subscribe
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.newRoot
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.lockscreen.impl.biometric.BiometricAuthenticatorManager
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.DefaultPinCodeManagerCallback
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.PinCodeManager
import tech.orbitweb.orbitx.features.lockscreen.impl.setup.biometric.SetupBiometricNode
import tech.orbitweb.orbitx.features.lockscreen.impl.setup.pin.SetupPinNode
import tech.orbitweb.orbitx.libraries.architecture.BackstackView
import tech.orbitweb.orbitx.libraries.architecture.BaseFlowNode
import tech.orbitweb.orbitx.libraries.architecture.callback
import tech.orbitweb.orbitx.libraries.architecture.createNode
import tech.orbitweb.orbitx.libraries.di.SessionScope

@ContributesNode(SessionScope::class)
@AssistedInject
class LockScreenSetupFlowNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    private val pinCodeManager: PinCodeManager,
    val biometricAuthenticatorManager: BiometricAuthenticatorManager,
) : BaseFlowNode<LockScreenSetupFlowNode.NavTarget>(
    backstack = BackStack(
        initialElement = NavTarget.Pin,
        savedStateMap = buildContext.savedStateMap,
    ),
    buildContext = buildContext,
    plugins = plugins,
) {
    interface Callback : Plugin {
        fun onSetupDone()
    }

    private val callback: Callback = callback()

    sealed interface NavTarget : Parcelable {
        @Parcelize
        data object Pin : NavTarget

        @Parcelize
        data object Biometric : NavTarget
    }

    private val pinCodeManagerCallback = object : DefaultPinCodeManagerCallback() {
        override fun onPinCodeCreated() {
            if (biometricAuthenticatorManager.hasAvailableAuthenticator) {
                backstack.newRoot(NavTarget.Biometric)
            } else {
                callback.onSetupDone()
            }
        }
    }

    init {
        lifecycle.subscribe(
            onCreate = {
                pinCodeManager.addCallback(pinCodeManagerCallback)
            },
            onDestroy = {
                pinCodeManager.removeCallback(pinCodeManagerCallback)
            }
        )
    }

    override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            NavTarget.Pin -> {
                createNode<SetupPinNode>(buildContext)
            }
            NavTarget.Biometric -> {
                val callback = object : SetupBiometricNode.Callback {
                    override fun onBiometricSetupDone() {
                        callback.onSetupDone()
                    }
                }
                createNode<SetupBiometricNode>(buildContext, plugins = listOf(callback))
            }
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        BackstackView()
    }
}
