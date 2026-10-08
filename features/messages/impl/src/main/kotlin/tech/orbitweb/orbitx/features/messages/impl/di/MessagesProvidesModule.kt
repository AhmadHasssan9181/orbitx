/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.di

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import tech.orbitweb.orbitx.features.messages.impl.timeline.di.LiveTimeline
import tech.orbitweb.orbitx.libraries.di.RoomScope
import tech.orbitweb.orbitx.libraries.matrix.api.room.JoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline

@ContributesTo(RoomScope::class)
@BindingContainer
object MessagesProvidesModule {
    @Provides
    @LiveTimeline
    fun provideLiveTimeline(joinedRoom: JoinedRoom): Timeline = joinedRoom.liveTimeline
}
