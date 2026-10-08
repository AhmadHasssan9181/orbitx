/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.preferences.impl.advanced

import androidx.activity.ComponentActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import com.google.common.truth.Truth.assertThat
import im.vector.app.features.analytics.plan.Interaction
import tech.orbitweb.orbitx.features.preferences.impl.R
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaPreviewValue
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsService
import tech.orbitweb.orbitx.services.analytics.compose.LocalAnalyticsService
import tech.orbitweb.orbitx.services.analytics.test.FakeAnalyticsService
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.assertNoNodeWithText
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import kotlinx.collections.immutable.toImmutableList
import org.junit.Test
import org.robolectric.annotation.Config

class AdvancedSettingsViewTest : RobolectricTest() {
    @Test
    fun `clicking on back invokes the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AdvancedSettingsEvents>(expectEvents = false)
        ensureCalledOnce {
            setAdvancedSettingsView(
                state = aAdvancedSettingsState(
                    eventSink = eventsRecorder
                ),
                onBackClick = it
            )
            pressBack()
        }
    }

    @Test
    fun `clicking on other theme emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AdvancedSettingsEvents>()
        setAdvancedSettingsView(
            state = aAdvancedSettingsState(
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.common_appearance)
        clickOn(R.string.theme_dark)
        eventsRecorder.assertSingle(AdvancedSettingsEvents.SetTheme(ThemeOption.Dark))
    }

    @Test
    fun `black theme is shown when available`() = runAndroidComposeUiTest {
        setAdvancedSettingsView(
            state = aAdvancedSettingsState(
                availableThemeOptions = ThemeOption.entries.toImmutableList(),
            ),
        )
        clickOn(CommonStrings.common_appearance)
        run {
            val text = activity!!.getString(R.string.theme_black)
            onNodeWithText(text).assertExists()
        }
    }

    @Test
    fun `black theme is hidden when unavailable`() = runAndroidComposeUiTest {
        setAdvancedSettingsView(
            state = aAdvancedSettingsState(
                availableThemeOptions = ThemeOption.entries.filterNot { it == ThemeOption.Black }.toImmutableList(),
            ),
        )
        clickOn(CommonStrings.common_appearance)
        assertNoNodeWithText(R.string.theme_black)
    }

    @Test
    fun `clicking on View source emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AdvancedSettingsEvents>()
        setAdvancedSettingsView(
            state = aAdvancedSettingsState(
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_view_source)
        eventsRecorder.assertSingle(AdvancedSettingsEvents.SetDeveloperModeEnabled(true))
    }

    @Test
    fun `clicking on Share presence emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AdvancedSettingsEvents>()
        setAdvancedSettingsView(
            state = aAdvancedSettingsState(
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_advanced_settings_share_presence)
        eventsRecorder.assertSingle(AdvancedSettingsEvents.SetSharePresenceEnabled(true))
    }

    @Test
    fun `clicking on media to enable compression emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AdvancedSettingsEvents>()
        val analyticsService = FakeAnalyticsService()
        setAdvancedSettingsView(
            state = aAdvancedSettingsState(
                eventSink = eventsRecorder,
            ),
            analyticsService = analyticsService
        )
        clickOn(R.string.screen_advanced_settings_media_compression_description)
        eventsRecorder.assertSingle(AdvancedSettingsEvents.SetCompressMedia(true))
        assertThat(analyticsService.capturedEvents).isEqualTo(
            listOf(
                Interaction(
                    name = Interaction.Name.MobileSettingsOptimizeMediaUploadsEnabled
                )
            )
        )
    }

    @Test
    fun `clicking on media to disable compression emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AdvancedSettingsEvents>()
        val analyticsService = FakeAnalyticsService()
        setAdvancedSettingsView(
            state = aAdvancedSettingsState(
                mediaOptimizationState = MediaOptimizationState.AllMedia(isEnabled = true),
                eventSink = eventsRecorder,
            ),
            analyticsService = analyticsService
        )
        clickOn(R.string.screen_advanced_settings_media_compression_description)
        eventsRecorder.assertSingle(AdvancedSettingsEvents.SetCompressMedia(false))
        assertThat(analyticsService.capturedEvents).isEqualTo(
            listOf(
                Interaction(
                    name = Interaction.Name.MobileSettingsOptimizeMediaUploadsDisabled
                )
            )
        )
    }

    @Test
    @Config(qualifiers = "h1080dp")
    fun `clicking on hide invite avatars emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AdvancedSettingsEvents>()
        setAdvancedSettingsView(
            state = aAdvancedSettingsState(
                eventSink = eventsRecorder,
                hideInviteAvatars = false
            ),
        )
        clickOn(R.string.screen_advanced_settings_hide_invite_avatars_toggle_title)
        eventsRecorder.assertSingle(AdvancedSettingsEvents.SetHideInviteAvatars(true))
    }

    @Test
    @Config(qualifiers = "h1080dp")
    fun `clicking on timeline media preview always hide emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AdvancedSettingsEvents>()
        setAdvancedSettingsView(
            state = aAdvancedSettingsState(
                eventSink = eventsRecorder,
                timelineMediaPreviewValue = MediaPreviewValue.On
            ),
        )
        clickOn(R.string.screen_advanced_settings_show_media_timeline_always_hide)
        eventsRecorder.assertSingle(AdvancedSettingsEvents.SetTimelineMediaPreviewValue(MediaPreviewValue.Off))
    }

    @Test
    @Config(qualifiers = "h1080dp")
    fun `clicking on timeline media preview private rooms emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AdvancedSettingsEvents>()
        setAdvancedSettingsView(
            state = aAdvancedSettingsState(
                eventSink = eventsRecorder,
                timelineMediaPreviewValue = MediaPreviewValue.On
            ),
        )
        clickOn(R.string.screen_advanced_settings_show_media_timeline_private_rooms)
        eventsRecorder.assertSingle(AdvancedSettingsEvents.SetTimelineMediaPreviewValue(MediaPreviewValue.Private))
    }

    @Test
    @Config(qualifiers = "h1080dp")
    fun `clicking on timeline media preview always show emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AdvancedSettingsEvents>()
        setAdvancedSettingsView(
            state = aAdvancedSettingsState(
                eventSink = eventsRecorder,
                timelineMediaPreviewValue = MediaPreviewValue.Off
            ),
        )
        clickOn(R.string.screen_advanced_settings_show_media_timeline_always_show)
        eventsRecorder.assertSingle(AdvancedSettingsEvents.SetTimelineMediaPreviewValue(MediaPreviewValue.On))
    }

    @Test
    @Config(qualifiers = "h1080dp")
    fun `hide invite avatars toggle is disabled when action is loading`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AdvancedSettingsEvents>(expectEvents = false)
        setAdvancedSettingsView(
            state = aAdvancedSettingsState(
                eventSink = eventsRecorder,
                hideInviteAvatars = false,
                setHideInviteAvatarsAction = AsyncAction.Loading
            ),
        )
        // The toggle should be disabled, so clicking should not emit any events
        clickOn(R.string.screen_advanced_settings_hide_invite_avatars_toggle_title)
    }

    @Test
    @Config(qualifiers = "h1080dp")
    fun `timeline media preview options are disabled when action is loading`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AdvancedSettingsEvents>(expectEvents = false)
        setAdvancedSettingsView(
            state = aAdvancedSettingsState(
                eventSink = eventsRecorder,
                timelineMediaPreviewValue = MediaPreviewValue.On,
                setTimelineMediaPreviewAction = AsyncAction.Loading
            ),
        )
        // The options should be disabled, so clicking should not emit any events
        clickOn(R.string.screen_advanced_settings_show_media_timeline_always_hide)
        clickOn(R.string.screen_advanced_settings_show_media_timeline_private_rooms)
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setAdvancedSettingsView(
    state: AdvancedSettingsState,
    analyticsService: AnalyticsService = FakeAnalyticsService(),
    onBackClick: () -> Unit = EnsureNeverCalled(),
    onOpenAppSettings: () -> Unit = EnsureNeverCalled(),
) {
    setContent {
        CompositionLocalProvider(
            LocalAnalyticsService provides analyticsService,
        ) {
            AdvancedSettingsView(
                state = state,
                onBackClick = onBackClick,
                onOpenAppSettingsClick = onOpenAppSettings
            )
        }
    }
}
