/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.api

import android.os.Parcelable
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.libraries.architecture.FeatureEntryPoint
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkData

interface MessagesEntryPoint : FeatureEntryPoint {
    sealed interface InitialTarget : Parcelable {
        @Parcelize
        data class Messages(
            val focusedEventId: EventId?,
        ) : InitialTarget

        @Parcelize
        data object PinnedMessages : InitialTarget
    }

    interface Callback : Plugin {
        fun navigateToRoomDetails()
        fun navigateToRoomMemberDetails(userId: UserId)
        fun handlePermalinkClick(data: PermalinkData, pushToBackstack: Boolean)
        fun forwardEvent(eventId: EventId, fromPinnedEvents: Boolean)
        fun navigateToRoom(roomId: RoomId)
        fun navigateToDeveloperSettings()
    }

    data class Params(val initialTarget: InitialTarget) : NodeInputs

    fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        params: Params,
        callback: Callback,
    ): Node

    interface NodeProxy {
        suspend fun attachThread(threadId: ThreadId, focusedEventId: EventId?)
    }
}
