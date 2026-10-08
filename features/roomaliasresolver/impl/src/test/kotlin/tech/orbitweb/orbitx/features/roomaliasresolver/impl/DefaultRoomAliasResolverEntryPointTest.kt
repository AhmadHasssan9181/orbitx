/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomaliasresolver.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bumble.appyx.core.modality.BuildContext
import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.roomaliasesolver.api.RoomAliasResolverEntryPoint
import tech.orbitweb.orbitx.libraries.matrix.api.room.alias.ResolvedRoomAlias
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ALIAS
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.node.TestParentNode
import org.junit.Rule
import org.junit.Test

class DefaultRoomAliasResolverEntryPointTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test node builder`() {
        val entryPoint = DefaultRoomAliasResolverEntryPoint()
        val parentNode = TestParentNode.create { buildContext, plugins ->
            RoomAliasResolverNode(
                buildContext = buildContext,
                plugins = plugins,
                presenterFactory = { alias ->
                    assertThat(alias).isEqualTo(A_ROOM_ALIAS)
                    createPresenter(
                        alias,
                    )
                }
            )
        }
        val callback = object : RoomAliasResolverEntryPoint.Callback {
            override fun onAliasResolved(data: ResolvedRoomAlias) = lambdaError()
        }
        val params = RoomAliasResolverEntryPoint.Params(
            roomAlias = A_ROOM_ALIAS
        )
        val result = entryPoint.createNode(
            parentNode = parentNode,
            buildContext = BuildContext.root(null),
            params = params,
            callback = callback,
        )
        assertThat(result).isInstanceOf(RoomAliasResolverNode::class.java)
        assertThat(result.plugins).contains(params)
        assertThat(result.plugins).contains(callback)
    }
}
