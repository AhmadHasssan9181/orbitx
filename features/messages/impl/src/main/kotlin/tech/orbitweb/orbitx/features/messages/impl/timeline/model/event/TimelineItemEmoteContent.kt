/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.model.event

import org.jsoup.nodes.Document
import tech.orbitweb.orbitx.libraries.matrix.ui.messages.toPlainText

data class TimelineItemEmoteContent(
    override val body: String,
    override val htmlDocument: Document?,
    override val formattedBody: CharSequence,
    override val isEdited: Boolean,
) : TimelineItemTextBasedContent {
    override val type: String = "TimelineItemEmoteContent"
    override val plainText: String = htmlDocument?.toPlainText() ?: body
}
