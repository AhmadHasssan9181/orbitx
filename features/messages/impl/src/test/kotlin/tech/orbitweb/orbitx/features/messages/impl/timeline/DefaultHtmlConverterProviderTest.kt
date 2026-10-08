/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.messages.impl.timeline

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.messages.impl.utils.FakeMentionSpanFormatter
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.permalink.FakePermalinkParser
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.MentionSpanProvider
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.MentionSpanTheme
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test

class DefaultHtmlConverterProviderTest : RobolectricTest() {
    private val provider = DefaultHtmlConverterProvider(
        mentionSpanProvider = MentionSpanProvider(
            permalinkParser = FakePermalinkParser(),
            mentionSpanFormatter = FakeMentionSpanFormatter(),
            mentionSpanTheme = MentionSpanTheme(A_USER_ID)
        )
    )

    @Test
    fun `calling provide without calling Update first should throw an exception`() {
        val exception = runCatchingExceptions { provider.provide() }.exceptionOrNull()

        assertThat(exception).isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `calling provide after calling Update first should return an HtmlConverter`() = runComposeUiTest {
        setContent {
            CompositionLocalProvider(LocalInspectionMode provides true) {
                provider.Update()
            }
        }
        val htmlConverter = runCatchingExceptions { provider.provide() }.getOrNull()

        assertThat(htmlConverter).isNotNull()
    }
}
