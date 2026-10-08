/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.signedout.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bumble.appyx.core.modality.BuildContext
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.features.signedout.api.SignedOutEntryPoint
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.tests.testutils.node.TestParentNode

class DefaultSignedOutEntryPointTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test node builder`() {
        val entryPoint = DefaultSignedOutEntryPoint()
        val parentNode = TestParentNode.create { buildContext, plugins ->
            SignedOutNode(
                buildContext = buildContext,
                plugins = plugins,
                presenterFactory = { sessionId ->
                    assertThat(sessionId).isEqualTo(A_SESSION_ID)
                    createSignedOutPresenter()
                }
            )
        }
        val params = SignedOutEntryPoint.Params(A_SESSION_ID)
        val result = entryPoint.createNode(
            parentNode = parentNode,
            buildContext = BuildContext.root(null),
            params = params,
        )
        assertThat(result).isInstanceOf(SignedOutNode::class.java)
        assertThat(result.plugins).contains(SignedOutNode.Inputs(params.sessionId))
    }
}
