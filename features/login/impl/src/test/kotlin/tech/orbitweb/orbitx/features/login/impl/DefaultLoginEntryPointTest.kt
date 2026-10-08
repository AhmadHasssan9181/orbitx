/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.testing.junit4.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.enterprise.test.FakeEnterpriseService
import tech.orbitweb.orbitx.features.login.api.LoginEntryPoint
import tech.orbitweb.orbitx.features.login.impl.accountprovider.AccountProviderDataSource
import tech.orbitweb.orbitx.features.login.impl.classic.FakeElementClassicConnection
import tech.orbitweb.orbitx.features.preferences.test.FakePreferencesEntryPoint
import tech.orbitweb.orbitx.libraries.oauth.test.customtab.FakeOAuthActionFlow
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.node.TestParentNode
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class DefaultLoginEntryPointTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `test node builder`() = runTest {
        val entryPoint = DefaultLoginEntryPoint()
        val parentNode = TestParentNode.create { buildContext, plugins ->
            LoginFlowNode(
                buildContext = buildContext,
                plugins = plugins,
                accountProviderDataSource = AccountProviderDataSource(FakeEnterpriseService()),
                oAuthActionFlow = FakeOAuthActionFlow(),
                appCoroutineScope = backgroundScope,
                elementClassicConnection = FakeElementClassicConnection(),
                preferencesEntryPoint = FakePreferencesEntryPoint(),
            )
        }
        val callback = object : LoginEntryPoint.Callback {
            override fun navigateToBugReport() = lambdaError()
            override fun onDone() = lambdaError()
        }
        val params = LoginEntryPoint.Params(
            accountProvider = "ac",
            loginHint = "lh",
        )
        val result = entryPoint.createNode(
            parentNode = parentNode,
            buildContext = BuildContext.root(null),
            params = params,
            callback = callback,
        )
        assertThat(result).isInstanceOf(LoginFlowNode::class.java)
        assertThat(result.plugins).contains(LoginFlowNode.Params(params.accountProvider, params.loginHint))
        assertThat(result.plugins).contains(callback)
    }
}
