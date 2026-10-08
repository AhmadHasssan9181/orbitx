/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.knockrequests.impl.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bumble.appyx.core.modality.BuildContext
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.tests.testutils.node.TestParentNode

class DefaultKnockRequestsListEntryPointTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test node builder`() = runTest {
        val entryPoint = DefaultKnockRequestsListEntryPoint()

        val parentNode = TestParentNode.create { buildContext, plugins ->
            KnockRequestsListNode(
                buildContext = buildContext,
                plugins = plugins,
                presenter = createKnockRequestsListPresenter(),
            )
        }
        val result = entryPoint.createNode(parentNode, BuildContext.root(null))
        assertThat(result).isInstanceOf(KnockRequestsListNode::class.java)
    }
}
