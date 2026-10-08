/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.accountselect.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bumble.appyx.core.modality.BuildContext
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.libraries.accountselect.api.AccountSelectEntryPoint
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.node.TestParentNode

class DefaultAccountSelectEntryPointTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test node builder`() {
        val entryPoint = DefaultAccountSelectEntryPoint()
        val parentNode = TestParentNode.create { buildContext, plugins ->
            AccountSelectNode(
                buildContext = buildContext,
                plugins = plugins,
                presenter = createAccountSelectPresenter(),
            )
        }
        val callback = object : AccountSelectEntryPoint.Callback {
            override fun onAccountSelected(sessionId: SessionId) = lambdaError()
            override fun onCancel() = lambdaError()
        }
        val result = entryPoint.createNode(
            parentNode = parentNode,
            buildContext = BuildContext.root(null),
            callback = callback,
        )
        assertThat(result).isInstanceOf(AccountSelectNode::class.java)
        assertThat(result.plugins).contains(callback)
    }
}
