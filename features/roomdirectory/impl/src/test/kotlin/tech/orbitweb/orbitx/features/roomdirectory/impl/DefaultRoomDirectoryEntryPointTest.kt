/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdirectory.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bumble.appyx.core.modality.BuildContext
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.features.roomdirectory.api.RoomDescription
import tech.orbitweb.orbitx.features.roomdirectory.api.RoomDirectoryEntryPoint
import tech.orbitweb.orbitx.features.roomdirectory.impl.root.RoomDirectoryNode
import tech.orbitweb.orbitx.features.roomdirectory.impl.root.createRoomDirectoryPresenter
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.node.TestParentNode

class DefaultRoomDirectoryEntryPointTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test node builder`() = runTest {
        val entryPoint = DefaultRoomDirectoryEntryPoint()
        val parentNode = TestParentNode.create { buildContext, plugins ->
            RoomDirectoryNode(
                buildContext = buildContext,
                plugins = plugins,
                presenter = createRoomDirectoryPresenter(),
            )
        }
        val callback = object : RoomDirectoryEntryPoint.Callback {
            override fun navigateToRoom(roomDescription: RoomDescription) = lambdaError()
        }
        val result = entryPoint.createNode(
            parentNode = parentNode,
            buildContext = BuildContext.root(null),
            callback = callback,
        )
        assertThat(result).isInstanceOf(RoomDirectoryNode::class.java)
        assertThat(result.plugins).contains(callback)
    }
}
