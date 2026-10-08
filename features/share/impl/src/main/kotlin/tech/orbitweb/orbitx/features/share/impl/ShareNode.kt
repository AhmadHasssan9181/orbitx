/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.share.impl

import android.os.Parcelable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.navigation.model.permanent.PermanentNavModel
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.share.api.ShareEntryPoint
import tech.orbitweb.orbitx.features.share.api.ShareIntentData
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.architecture.callback
import tech.orbitweb.orbitx.libraries.architecture.inputs
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.roomselect.api.RoomSelectEntryPoint
import tech.orbitweb.orbitx.libraries.roomselect.api.RoomSelectMode

@ContributesNode(SessionScope::class)
@AssistedInject
class ShareNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: SharePresenter.Factory,
    private val roomSelectEntryPoint: RoomSelectEntryPoint,
) : ParentNode<ShareNode.NavTarget>(
    navModel = PermanentNavModel(
        navTargets = setOf(NavTarget),
        savedStateMap = buildContext.savedStateMap,
    ),
    buildContext = buildContext,
    plugins = plugins,
) {
    @Parcelize
    object NavTarget : Parcelable

    data class Inputs(val shareIntentData: ShareIntentData) : NodeInputs

    private val inputs = inputs<Inputs>()
    private val presenter = presenterFactory.create(inputs.shareIntentData)
    private val callback: ShareEntryPoint.Callback = callback()

    override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node {
        val callback = object : RoomSelectEntryPoint.Callback {
            override fun onRoomSelected(roomIds: List<RoomId>) {
                presenter.onRoomSelected(roomIds)
            }

            override fun onCancel() {
                callback.onDone(emptyList())
            }
        }

        return roomSelectEntryPoint.createNode(
            parentNode = this,
            buildContext = buildContext,
            params = RoomSelectEntryPoint.Params(
                mode = RoomSelectMode.Share,
                maxNumberOfRooms = RoomSelectEntryPoint.DEFAULT_MAX_NUMBER_OF_ROOMS,
            ),
            callback = callback,
        )
    }

    @Composable
    override fun View(modifier: Modifier) {
        Box(modifier = modifier) {
            // Will render to room select screen
            Children(
                navModel = navModel,
            )

            val state = presenter.present()
            ShareView(
                state = state,
                onShareSuccess = callback::onDone,
            )
        }
    }
}
