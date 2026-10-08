/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.createroom.api

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import tech.orbitweb.orbitx.libraries.architecture.FeatureEntryPoint
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

interface CreateRoomEntryPoint : FeatureEntryPoint {
    interface Builder {
        fun setIsSpace(isSpace: Boolean): Builder
        fun setParentSpace(parentSpaceId: RoomId): Builder
        fun build(): Node
    }

    fun builder(parentNode: Node, buildContext: BuildContext, callback: Callback): Builder

    interface Callback : Plugin {
        fun onRoomCreated(roomId: RoomId)
    }
}
