/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.link

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.tests.testutils.WarmUpRule
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value
import tech.orbitweb.orbitx.tests.testutils.test
import io.element.android.wysiwyg.link.Link
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

val aLink = Link(url = "url", text = "text")

class LinkPresenterTest {
    @get:Rule
    val warmUpRule = WarmUpRule()

    @Test
    fun `present - initial state`() = runTest {
        val presenter = createPresenter()
        presenter.test {
            val initialState = awaitItem()
            assertThat(initialState.linkClick).isEqualTo(AsyncAction.Uninitialized)
        }
    }

    @Test
    fun `present - safe link case`() = runTest {
        val isSafeResult = lambdaRecorder<Link, Boolean> {
            true
        }
        val presenter = createPresenter(
            linkChecker = FakeLinkChecker(isSafeResult = isSafeResult)
        )
        presenter.test {
            val initialState = awaitItem()
            assertThat(initialState.linkClick).isEqualTo(AsyncAction.Uninitialized)
            initialState.eventSink(LinkEvent.OnLinkClick(aLink))
            assertThat(awaitItem().linkClick).isEqualTo(AsyncAction.Loading)
            val state = awaitItem()
            assertThat(state.linkClick).isEqualTo(AsyncAction.Success(aLink))
            isSafeResult.assertions().isCalledOnce().with(value(aLink))
        }
    }

    @Test
    fun `present - suspicious link case - cancel`() = runTest {
        val presenter = createPresenter(
            linkChecker = FakeLinkChecker(isSafeResult = { false })
        )
        presenter.test {
            val initialState = awaitItem()
            assertThat(initialState.linkClick).isEqualTo(AsyncAction.Uninitialized)
            initialState.eventSink(LinkEvent.OnLinkClick(aLink))
            assertThat(awaitItem().linkClick).isEqualTo(AsyncAction.Loading)
            val state = awaitItem()
            assertThat(state.linkClick).isEqualTo(ConfirmingLinkClick(aLink))
            state.eventSink(LinkEvent.Cancel)
            val finalState = awaitItem()
            assertThat(finalState.linkClick).isEqualTo(AsyncAction.Uninitialized)
        }
    }

    @Test
    fun `present - suspicious link case - confirm`() = runTest {
        val presenter = createPresenter(
            linkChecker = FakeLinkChecker(isSafeResult = { false })
        )
        presenter.test {
            val initialState = awaitItem()
            assertThat(initialState.linkClick).isEqualTo(AsyncAction.Uninitialized)
            initialState.eventSink(LinkEvent.OnLinkClick(aLink))
            assertThat(awaitItem().linkClick).isEqualTo(AsyncAction.Loading)
            val state = awaitItem()
            assertThat(state.linkClick).isEqualTo(ConfirmingLinkClick(aLink))
            state.eventSink(LinkEvent.Confirm)
            val finalState = awaitItem()
            assertThat(finalState.linkClick).isEqualTo(AsyncAction.Success(aLink))
        }
    }

    private fun createPresenter(
        linkChecker: LinkChecker = FakeLinkChecker(),
    ) = LinkPresenter(
        linkChecker = linkChecker,
    )
}
