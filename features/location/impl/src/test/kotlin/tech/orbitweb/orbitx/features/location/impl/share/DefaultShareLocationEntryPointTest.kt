/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl.share

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bumble.appyx.core.modality.BuildContext
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.features.location.impl.common.FakeUserLocationStateFactory
import tech.orbitweb.orbitx.features.location.impl.common.actions.FakeLocationActions
import tech.orbitweb.orbitx.features.location.impl.common.permissions.FakePermissionsPresenter
import tech.orbitweb.orbitx.features.location.impl.live.LiveLocationStore
import tech.orbitweb.orbitx.features.location.test.FakeActiveLiveLocationShareManager
import tech.orbitweb.orbitx.features.messages.test.FakeMessageComposerContext
import tech.orbitweb.orbitx.libraries.dateformatter.test.FakeDurationFormatter
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.libraries.matrix.test.core.aBuildMeta
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeJoinedRoom
import tech.orbitweb.orbitx.libraries.preferences.test.FakePreferenceDataStoreFactory
import tech.orbitweb.orbitx.services.analytics.test.FakeAnalyticsService
import tech.orbitweb.orbitx.tests.testutils.node.TestParentNode

class DefaultShareLocationEntryPointTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test node builder`() = runTest {
        val entryPoint = DefaultShareLocationEntryPoint()
        val parentNode = TestParentNode.create { buildContext, plugins ->
            val room = FakeJoinedRoom()
            ShareLocationNode(
                buildContext = buildContext,
                plugins = plugins,
                presenterFactory = { timelineMode: Timeline.Mode ->
                    ShareLocationPresenter(
                        permissionsPresenterFactory = { FakePermissionsPresenter() },
                        room = room,
                        timelineMode = timelineMode,
                        analyticsService = FakeAnalyticsService(),
                        messageComposerContext = FakeMessageComposerContext(),
                        locationActions = FakeLocationActions(),
                        buildMeta = aBuildMeta(),
                        client = FakeMatrixClient(),
                        durationFormatter = FakeDurationFormatter(),
                        liveLocationShareManager = FakeActiveLiveLocationShareManager(),
                        liveLocationStore = LiveLocationStore(
                            preferenceDataStoreFactory = FakePreferenceDataStoreFactory(),
                            sessionId = room.sessionId,
                        ),
                        userLocationStateFactory = FakeUserLocationStateFactory(),
                    )
                },
                analyticsService = FakeAnalyticsService(),
            )
        }
        val timelineMode = Timeline.Mode.Live
        val result = entryPoint.createNode(
            parentNode = parentNode,
            buildContext = BuildContext.root(null),
            timelineMode = timelineMode,
        )
        assertThat(result).isInstanceOf(ShareLocationNode::class.java)
        assertThat(result.plugins).contains(ShareLocationNode.Inputs(timelineMode))
    }
}
