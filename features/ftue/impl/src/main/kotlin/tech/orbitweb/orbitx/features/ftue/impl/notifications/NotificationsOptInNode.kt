/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.ftue.impl.notifications

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.architecture.inputs

@ContributesNode(AppScope::class)
@AssistedInject
class NotificationsOptInNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: NotificationsOptInPresenter.Factory,
) : Node(buildContext, plugins = plugins) {
    interface Callback : NodeInputs {
        fun onNotificationsOptInFinished()
    }

    private val callback = inputs<Callback>()

    private val presenter: NotificationsOptInPresenter = presenterFactory.create(callback)

    @Composable
    override fun View(modifier: Modifier) {
        val state = presenter.present()
        NotificationsOptInView(
            state = state,
            onBack = { callback.onNotificationsOptInFinished() },
            modifier = modifier
        )
    }
}
