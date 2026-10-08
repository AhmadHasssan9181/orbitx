/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.device

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.lockscreen.impl.unlock.PinUnlockPresenter
import tech.orbitweb.orbitx.features.lockscreen.impl.unlock.PinUnlockView
import tech.orbitweb.orbitx.libraries.di.SessionScope

@ContributesNode(SessionScope::class)
@AssistedInject
class DeviceUnlockNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    private val presenter: DeviceUnlockPresenter,
    private val pinUnlockPresenterFactory: PinUnlockPresenter.Factory,
) : Node(buildContext, plugins = plugins) {
    @Composable
    override fun View(modifier: Modifier) {
        val state = presenter.present()
        if (state.showApplicationPinCode) {
            val pinUnlockPresenter = remember {
                pinUnlockPresenterFactory.create(forDeviceUnlock = true)
            }
            val pinState = pinUnlockPresenter.present()
            PinUnlockView(
                state = pinState,
                isInAppUnlock = true,
                onCancel = {
                    state.eventSink(DeviceUnlockEvent.CancelPinCode)
                },
                modifier = modifier,
            )
        }
    }
}
