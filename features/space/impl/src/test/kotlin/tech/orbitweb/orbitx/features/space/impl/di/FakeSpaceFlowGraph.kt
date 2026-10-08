/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.space.impl.di

import com.bumble.appyx.core.node.Node
import tech.orbitweb.orbitx.libraries.architecture.AssistedNodeFactory
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoomList
import kotlin.reflect.KClass

class FakeSpaceFlowGraph : SpaceFlowGraph {
    object Factory : SpaceFlowGraph.Factory {
        override fun create(spaceRoomList: SpaceRoomList): SpaceFlowGraph {
            return FakeSpaceFlowGraph()
        }
    }

    override fun nodeFactories(): Map<KClass<out Node>, AssistedNodeFactory<*>> {
        return emptyMap()
    }
}
