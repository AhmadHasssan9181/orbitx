/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.eventformatter.test

import tech.orbitweb.orbitx.libraries.eventformatter.api.PinnedMessagesBannerFormatter
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventTimelineItem

class FakePinnedMessagesBannerFormatter(
    val formatLambda: (event: EventTimelineItem) -> CharSequence
) : PinnedMessagesBannerFormatter {
    override fun format(event: EventTimelineItem): CharSequence {
        return formatLambda(event)
    }
}
