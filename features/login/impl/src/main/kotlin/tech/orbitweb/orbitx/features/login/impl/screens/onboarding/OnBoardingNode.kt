/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.onboarding

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

@ContributesNode(AppScope::class)
@AssistedInject
class OnBoardingNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: OnBoardingPresenter.Factory,
) : Node(
    buildContext = buildContext,
    plugins = plugins
) {
    interface Callback : Plugin {
        fun navigateToSignUpFlow()
        fun navigateToSignInFlow(mustChooseAccountProvider: Boolean)
        fun navigateToQrCode()
        fun navigateToBugReport()
        fun navigateToLoginPassword()
        fun navigateToOAuth(oAuthDetails: OAuthDetails)
        fun navigateToCreateAccount(url: String)
        fun navigateToDeveloperSettings()
        fun onDone()
    }

    data class Params(
        val accountProvider: String?,
        val loginHint: String?,
        val showBackButton: Boolean,
    ) : NodeInputs

    private val callback: Callback = callback()
    private val params = inputs<Params>()

    private val presenter = presenterFactory.create(
        params = params,
    )

    @Composable
    override fun View(modifier: Modifier) {
        val state = presenter.present()
        val context = LocalContext.current

        OnBoardingView(
            state = state,
            modifier = modifier,
            onSignIn = callback::navigateToSignInFlow,
            onCreateAccount = callback::navigateToSignUpFlow,
            onSignInWithQrCode = callback::navigateToQrCode,
            onReportProblem = callback::navigateToBugReport,
            onOAuthDetails = callback::navigateToOAuth,
            onNeedLoginPassword = callback::navigateToLoginPassword,
            onLearnMoreClick = { openLearnMorePage(context) },
            onCreateAccountContinue = callback::navigateToCreateAccount,
            onBackClick = callback::onDone,
            onDeveloperSettingsClick = callback::navigateToDeveloperSettings,
        )
    }
}
