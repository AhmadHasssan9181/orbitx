/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl.show

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bumble.appyx.core.modality.BuildContext
import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.location.api.Location
import tech.orbitweb.orbitx.features.location.api.ShowLocationEntryPoint
import tech.orbitweb.orbitx.features.location.api.ShowLocationMode
import tech.orbitweb.orbitx.features.location.impl.common.FakeUserLocationStateFactory
import tech.orbitweb.orbitx.features.location.impl.common.actions.FakeLocationActions
import tech.orbitweb.orbitx.features.location.impl.common.permissions.FakePermissionsPresenter
import tech.orbitweb.orbitx.features.location.test.FakeActiveLiveLocationShareManager
import tech.orbitweb.orbitx.libraries.dateformatter.test.FakeDateFormatter
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.libraries.matrix.test.core.aBuildMeta
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeJoinedRoom
import tech.orbitweb.orbitx.services.analytics.test.FakeAnalyticsService
import tech.orbitweb.orbitx.services.toolbox.test.strings.FakeStringProvider
import tech.orbitweb.orbitx.tests.testutils.node.TestParentNode
import org.junit.Rule
import org.junit.Test

class DefaultShowLocationEntryPointTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test node builder`() {
        val entryPoint = DefaultShowLocationEntryPoint()
        val parentNode = TestParentNode.create { buildContext, plugins ->
            val joinedRoom = FakeJoinedRoom()
            ShowLocationNode(
                buildContext = buildContext,
                plugins = plugins,
                presenterFactory = object : ShowLocationPresenter.Factory {
                    override fun create(mode: ShowLocationMode) = ShowLocationPresenter(
                        mode = mode,
                        permissionsPresenterFactory = { FakePermissionsPresenter() },
                        locationActions = FakeLocationActions(),
                        buildMeta = aBuildMeta(),
                        dateFormatter = FakeDateFormatter(),
                        stringProvider = FakeStringProvider(),
                        joinedRoom = joinedRoom,
                        client = FakeMatrixClient(),
                        liveLocationShareManager = FakeActiveLiveLocationShareManager(),
                        userLocationStateFactory = FakeUserLocationStateFactory(),
                    )
                },
                analyticsService = FakeAnalyticsService(),
            )
        }
        val inputs = ShowLocationEntryPoint.Inputs(
            mode = ShowLocationMode.Static(
                location = Location(37.4219983, -122.084, 10f),
                senderName = "Alice",
                senderId = UserId("@alice:matrix.org"),
                senderAvatarUrl = null,
                timestamp = System.currentTimeMillis(),
                assetType = null,
            ),
        )
        val result = entryPoint.createNode(
            parentNode = parentNode,
            buildContext = BuildContext.root(null),
            inputs = inputs,
        )
        assertThat(result).isInstanceOf(ShowLocationNode::class.java)
        assertThat(result.plugins).contains(inputs)
    }
}
