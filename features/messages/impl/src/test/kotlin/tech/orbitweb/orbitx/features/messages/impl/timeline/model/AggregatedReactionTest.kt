/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.model

import org.junit.Assert.assertEquals
import org.junit.Test

class AggregatedReactionTest {
    @Test
    fun `reaction display key is shortened`() {
        val reaction = anAggregatedReaction(
            key = "1234567890123456790",
            count = 1
        )

        assertEquals("1234567890123456…", reaction.displayKey)
    }

    @Test
    fun `reaction count and isHighlighted are computed correctly`() {
        val reaction = anAggregatedReaction(
            key = "1234567890123456790",
            count = 3,
            isHighlighted = true
        )

        assertEquals(3, reaction.count)
        assertEquals(true, reaction.isHighlighted)
    }
}
