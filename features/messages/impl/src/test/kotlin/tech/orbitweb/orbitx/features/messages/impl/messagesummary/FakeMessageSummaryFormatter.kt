/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.messagesummary

import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemEventContent
import tech.orbitweb.orbitx.features.messages.impl.utils.messagesummary.MessageSummaryFormatter

class FakeMessageSummaryFormatter : MessageSummaryFormatter {
    private var result = "A message"

    override fun format(content: TimelineItemEventContent): String = result

    fun givenMessageResult(value: String) {
        result = value
    }
}
