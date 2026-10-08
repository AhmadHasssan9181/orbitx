/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.classic.loginwithclassic

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.login.impl.util.openLearnMorePage
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.architecture.callback
import tech.orbitweb.orbitx.libraries.architecture.inputs
import tech.orbitweb.orbitx.libraries.matrix.api.auth.OAuthDetails
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId

@ContributesNode(AppScope::class)
@AssistedInject
class LoginWithClassicNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: LoginWithClassicPresenter.Factory,
) : Node(buildContext, plugins = plugins),
    LoginWithClassicNavigator {
    interface Callback : Plugin {
        fun navigateToOtherOptions()
        fun navigateToLoginPassword()
        fun navigateToOAuth(oAuthDetails: OAuthDetails)
        fun navigateToCreateAccount(url: String)
        fun navigateToMissingKeyBackup()
    }

    data class Inputs(
        val userId: UserId,
    ) : NodeInputs

    private val inputs: Inputs = inputs()
    val presenter = presenterFactory.create(inputs.userId, this)
    private val callback: Callback = callback()

    override fun navigateToMissingKeyBackup() {
        callback.navigateToMissingKeyBackup()
    }

    @Composable
    override fun View(modifier: Modifier) {
        val context = LocalContext.current
        val state = presenter.present()
        LoginWithClassicView(
            state = state,
            modifier = modifier,
            onOtherOptionsClick = callback::navigateToOtherOptions,
            onOAuthDetails = callback::navigateToOAuth,
            onNeedLoginPassword = callback::navigateToLoginPassword,
            onLearnMoreClick = { openLearnMorePage(context) },
            onCreateAccountContinue = callback::navigateToCreateAccount,
        )
    }
}
