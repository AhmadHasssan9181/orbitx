/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.poll.test.pollcontent

import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.features.poll.api.pollcontent.PollAnswerItem
import tech.orbitweb.orbitx.features.poll.api.pollcontent.PollContentState
import tech.orbitweb.orbitx.features.poll.api.pollcontent.PollContentStateFactory
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.PollContent

class FakePollContentStateFactory : PollContentStateFactory {
    override suspend fun create(eventId: EventId?, isEditable: Boolean, isOwn: Boolean, content: PollContent): PollContentState {
        return PollContentState(
            eventId = eventId,
            question = content.question,
            answerItems = emptyList<PollAnswerItem>().toImmutableList(),
            pollKind = content.kind,
            isPollEditable = isEditable,
            isPollEnded = content.endTime != null,
            isMine = isOwn,
        )
    }
}
