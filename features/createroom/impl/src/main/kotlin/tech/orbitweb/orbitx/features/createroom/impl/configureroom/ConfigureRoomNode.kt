/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.createroom.impl.configureroom

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.lifecycle.subscribe
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import im.vector.app.features.analytics.plan.MobileScreen
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.architecture.callback
import tech.orbitweb.orbitx.libraries.architecture.inputs
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsService

@ContributesNode(SessionScope::class)
@AssistedInject
class ConfigureRoomNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: ConfigureRoomPresenter.Factory,
    private val analyticsService: AnalyticsService,
) : Node(buildContext, plugins = plugins) {
    interface Callback : Plugin {
        fun onCreateRoomSuccess(roomId: RoomId)
    }

    @Parcelize
    data class Inputs(
        val isSpace: Boolean,
        val parentSpaceId: RoomId?,
    ) : NodeInputs, Parcelable

    private val inputs = inputs<Inputs>()

    private val presenter = presenterFactory.create(inputs.isSpace, inputs.parentSpaceId)

    init {
        lifecycle.subscribe(
            onResume = {
                analyticsService.screen(MobileScreen(screenName = MobileScreen.ScreenName.CreateRoom))
            }
        )
    }

    private val callback: Callback = callback()

    @Composable
    override fun View(modifier: Modifier) {
        val state = presenter.present()
        ConfigureRoomView(
            state = state,
            modifier = modifier,
            onBackClick = this::navigateUp,
            onCreateRoomSuccess = callback::onCreateRoomSuccess,
        )
    }
}
