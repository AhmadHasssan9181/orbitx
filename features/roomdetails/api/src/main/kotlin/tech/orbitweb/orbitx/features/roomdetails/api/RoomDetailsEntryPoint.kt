/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdetails.api

import android.os.Parcelable
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.libraries.architecture.FeatureEntryPoint
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkData

interface RoomDetailsEntryPoint : FeatureEntryPoint {
    sealed interface InitialTarget : Parcelable {
        @Parcelize
        data object RoomDetails : InitialTarget

        @Parcelize
        data object RoomMemberList : InitialTarget

        @Parcelize
        data class RoomMemberDetails(val roomMemberId: UserId) : InitialTarget

        @Parcelize
        data object RoomNotificationSettings : InitialTarget
    }

    data class Params(val initialElement: InitialTarget) : NodeInputs

    interface Callback : Plugin {
        fun onDone()
        fun navigateToGlobalNotificationSettings()
        fun navigateToDeveloperSettings()
        fun navigateToRoom(roomId: RoomId, serverNames: List<String>, clearBackStack: Boolean = false)
        fun handlePermalinkClick(data: PermalinkData, pushToBackstack: Boolean)
        fun startForwardEventFlow(eventId: EventId, fromPinnedEvents: Boolean)
    }

    fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        params: Params,
        callback: Callback,
    ): Node
}
