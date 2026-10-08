/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.link

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.element.android.wysiwyg.link.Link
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction

open class LinkStateProvider : PreviewParameterProvider<LinkState> {
    override val values: Sequence<LinkState>
        get() = sequenceOf(
            aLinkState(),
            aLinkState(
                linkClick = ConfirmingLinkClick(
                    Link(
                        url = "https://evil.io",
                        text = "https://orbitweb.tech"
                    ),
                ),
            ),
        )
}

fun aLinkState(
    linkClick: AsyncAction<Link> = AsyncAction.Uninitialized,
    eventSink: (LinkEvent) -> Unit = {},
) = LinkState(
    linkClick = linkClick,
    eventSink = eventSink,
)
