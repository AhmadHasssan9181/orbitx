/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.linknewdevice.impl.screens.qrcode

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.architecture.callback
import tech.orbitweb.orbitx.libraries.architecture.inputs
import tech.orbitweb.orbitx.libraries.di.SessionScope

@ContributesNode(SessionScope::class)
@AssistedInject
class ShowQrCodeNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    showQrCodePresenterFactory: ShowQrCodePresenter.Factory,
) : Node(buildContext, plugins = plugins) {
    class Inputs(
        val data: String,
    ) : NodeInputs

    interface Callback : Plugin {
        fun navigateBack()
    }

    private val inputs: Inputs = inputs<Inputs>()
    private val callback: Callback = callback()
    private val showQrCodePresenter: ShowQrCodePresenter = showQrCodePresenterFactory.create(
        initialData = inputs.data,
    )

    @Composable
    override fun View(modifier: Modifier) {
        val state = showQrCodePresenter.present()
        ShowQrCodeView(
            state = state,
            modifier = modifier,
            onBackClick = callback::navigateBack,
        )
    }
}
