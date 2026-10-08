/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.troubleshoot.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bumble.appyx.core.modality.BuildContext
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.libraries.troubleshoot.api.NotificationTroubleShootEntryPoint
import tech.orbitweb.orbitx.services.analytics.test.FakeScreenTracker
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.node.TestParentNode

class DefaultNotificationTroubleShootEntryPointTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test node builder`() {
        val entryPoint = DefaultNotificationTroubleShootEntryPoint()
        val parentNode = TestParentNode.create { buildContext, plugins ->
            TroubleshootNotificationsNode(
                buildContext = buildContext,
                plugins = plugins,
                factory = { createTroubleshootNotificationsPresenter() },
                screenTracker = FakeScreenTracker(),
            )
        }
        val callback = object : NotificationTroubleShootEntryPoint.Callback {
            override fun onDone() = lambdaError()
            override fun navigateToBlockedUsers() = lambdaError()
        }
        val result = entryPoint.createNode(
            parentNode = parentNode,
            buildContext = BuildContext.root(null),
            callback = callback,
        )
        assertThat(result).isInstanceOf(TroubleshootNotificationsNode::class.java)
        assertThat(result.plugins).contains(callback)
    }
}
