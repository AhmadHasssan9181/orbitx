/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.startchat.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.testing.junit4.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.features.createroom.api.FakeCreateRoomEntryPoint
import tech.orbitweb.orbitx.features.startchat.api.StartChatEntryPoint
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomIdOrAlias
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.node.TestParentNode

class DefaultStartChatEntryPointTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `test node builder`() {
        val entryPoint = DefaultStartChatEntryPoint()
        val parentNode = TestParentNode.create { buildContext, plugins ->
            StartChatFlowNode(
                buildContext = buildContext,
                plugins = plugins,
                createRoomEntryPoint = FakeCreateRoomEntryPoint(),
            )
        }
        val callback = object : StartChatEntryPoint.Callback {
            override fun onRoomCreated(roomIdOrAlias: RoomIdOrAlias, serverNames: List<String>) = lambdaError()
            override fun navigateToRoomDirectory() = lambdaError()
        }
        val result = entryPoint.createNode(
            parentNode = parentNode,
            buildContext = BuildContext.root(null),
            callback = callback,
        )
        assertThat(result).isInstanceOf(StartChatFlowNode::class.java)
        assertThat(result.plugins).contains(callback)
    }
}
