/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.api

import android.os.Parcelable
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.libraries.architecture.FeatureEntryPoint
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

interface PreferencesEntryPoint : FeatureEntryPoint {
    sealed interface InitialTarget : Parcelable {
        @Parcelize
        data object Root : InitialTarget

        @Parcelize
        data object NotificationSettings : InitialTarget

        @Parcelize
        data object NotificationTroubleshoot : InitialTarget

        @Parcelize
        data object DeveloperSettings : InitialTarget
    }

    data class Params(val initialElement: InitialTarget) : NodeInputs

    fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        params: Params,
        callback: Callback,
    ): Node

    interface Callback : Plugin {
        fun navigateToAddAccount()
        fun navigateToLinkNewDevice()
        fun navigateToBugReport()
        fun navigateToSecureBackup()
        fun navigateToRoomNotificationSettings(roomId: RoomId)
        fun navigateToEvent(roomId: RoomId, eventId: EventId)
    }

    fun createAppDeveloperSettingsNode(
        parentNode: Node,
        buildContext: BuildContext,
        callback: DeveloperSettingsCallback,
    ): Node

    interface DeveloperSettingsCallback : Plugin {
        fun onDone()
    }
}
