/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.voicemessages.timeline

import tech.orbitweb.orbitx.libraries.matrix.api.timeline.MatrixTimelineItem

class FakeRedactedVoiceMessageManager : RedactedVoiceMessageManager {
    private val _invocations: MutableList<List<MatrixTimelineItem>> = mutableListOf()
    val invocations: List<List<MatrixTimelineItem>>
        get() = _invocations

    override suspend fun onEachMatrixTimelineItem(timelineItems: List<MatrixTimelineItem>) {
        _invocations.add(timelineItems)
    }
}
