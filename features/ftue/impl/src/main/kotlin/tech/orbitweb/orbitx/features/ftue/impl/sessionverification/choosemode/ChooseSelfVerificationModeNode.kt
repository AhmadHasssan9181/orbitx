/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.ftue.impl.sessionverification.choosemode

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.logout.api.direct.DirectLogoutView
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.architecture.callback
import tech.orbitweb.orbitx.libraries.di.SessionScope

@ContributesNode(SessionScope::class)
@AssistedInject
class ChooseSelfVerificationModeNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    private val presenter: Presenter<ChooseSelfVerificationModeState>,
    private val directLogoutView: DirectLogoutView,
) : Node(buildContext, plugins = plugins) {
    interface Callback : Plugin {
        fun navigateToUseAnotherDevice()
        fun navigateToUseRecoveryKey()
        fun navigateToResetKey()
        fun navigateToLearnMoreAboutEncryption()
    }

    private val callback: Callback = callback()

    @Composable
    override fun View(modifier: Modifier) {
        val state = presenter.present()

        ChooseSelfVerificationModeView(
            state = state,
            onUseAnotherDevice = callback::navigateToUseAnotherDevice,
            onUseRecoveryKey = callback::navigateToUseRecoveryKey,
            onResetKey = callback::navigateToResetKey,
            onLearnMore = callback::navigateToLearnMoreAboutEncryption,
            modifier = modifier,
        )

        directLogoutView.Render(state = state.directLogoutState)
    }
}
