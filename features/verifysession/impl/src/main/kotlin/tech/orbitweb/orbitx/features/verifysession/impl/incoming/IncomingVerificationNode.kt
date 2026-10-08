/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.verifysession.impl.incoming

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.verifysession.api.IncomingVerificationEntryPoint
import tech.orbitweb.orbitx.libraries.architecture.callback
import tech.orbitweb.orbitx.libraries.architecture.inputs
import tech.orbitweb.orbitx.libraries.di.SessionScope

@ContributesNode(SessionScope::class)
@AssistedInject
class IncomingVerificationNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: IncomingVerificationPresenter.Factory,
) : Node(buildContext, plugins = plugins),
    IncomingVerificationNavigator {
    private val callback: IncomingVerificationEntryPoint.Callback = callback()
    private val presenter = presenterFactory.create(
        verificationRequest = inputs<IncomingVerificationEntryPoint.Params>().verificationRequest,
        navigator = this,
    )

    override fun onFinish() {
        callback.onDone()
    }

    @Composable
    override fun View(modifier: Modifier) {
        val state = presenter.present()
        IncomingVerificationView(
            state = state,
            modifier = modifier,
        )
    }
}
