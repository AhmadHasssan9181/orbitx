/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.troubleshoot.impl.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import im.vector.app.features.analytics.plan.MobileScreen
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.libraries.architecture.callback
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.troubleshoot.api.PushHistoryEntryPoint
import tech.orbitweb.orbitx.services.analytics.api.ScreenTracker

@ContributesNode(SessionScope::class)
@AssistedInject
class PushHistoryNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: PushHistoryPresenter.Factory,
    private val screenTracker: ScreenTracker,
) : Node(buildContext, plugins = plugins), PushHistoryNavigator {
    private val callback: PushHistoryEntryPoint.Callback = callback()

    override fun navigateTo(roomId: RoomId, eventId: EventId) {
        callback.navigateToEvent(roomId, eventId)
    }

    private val presenter = presenterFactory.create(this)

    @Composable
    override fun View(modifier: Modifier) {
        screenTracker.TrackScreen(MobileScreen.ScreenName.NotificationTroubleshoot)
        val state = presenter.present()
        PushHistoryView(
            state = state,
            onBackClick = callback::onDone,
            modifier = modifier,
        )
    }
}
