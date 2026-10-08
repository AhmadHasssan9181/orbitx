/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invite.impl.declineandblock

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bumble.appyx.core.modality.BuildContext
import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.invite.test.anInviteData
import tech.orbitweb.orbitx.tests.testutils.node.TestParentNode
import org.junit.Rule
import org.junit.Test

class DefaultDeclineAndBlockEntryPointTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test node builder`() {
        val entryPoint = DefaultDeclineAndBlockEntryPoint()
        val parentNode = TestParentNode.create { buildContext, plugins ->
            DeclineAndBlockNode(
                buildContext = buildContext,
                plugins = plugins,
                presenterFactory = { inviteData -> createDeclineAndBlockPresenter() }
            )
        }
        val inviteData = anInviteData()
        val result = entryPoint.createNode(
            parentNode = parentNode,
            buildContext = BuildContext.root(null),
            inviteData = inviteData
        )
        assertThat(result).isInstanceOf(DeclineAndBlockNode::class.java)
        assertThat(result.plugins).contains(DeclineAndBlockNode.Inputs(inviteData))
    }
}
