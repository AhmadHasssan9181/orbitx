/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.poll.impl.actions

import dev.zacsweers.metro.ContributesBinding
import im.vector.app.features.analytics.plan.PollVote
import tech.orbitweb.orbitx.features.poll.api.actions.SendPollResponseAction
import tech.orbitweb.orbitx.libraries.di.RoomScope
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsService

@ContributesBinding(RoomScope::class)
class DefaultSendPollResponseAction(
    private val analyticsService: AnalyticsService,
) : SendPollResponseAction {
    override suspend fun execute(timeline: Timeline, pollStartId: EventId, answerId: String): Result<Unit> {
        return timeline.sendPollResponse(
            pollStartId = pollStartId,
            answers = listOf(answerId),
        ).onSuccess {
            analyticsService.capture(PollVote())
        }
    }
}
