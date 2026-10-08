/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roommembermoderation.impl.di

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import tech.orbitweb.orbitx.features.roommembermoderation.api.RoomMemberModerationState
import tech.orbitweb.orbitx.features.roommembermoderation.impl.RoomMemberModerationPresenter
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.di.RoomScope

@ContributesTo(RoomScope::class)
@BindingContainer
interface RoomMemberModerationModule {
    @Binds
    fun bindRoomMemberModerationPresenter(presenter: RoomMemberModerationPresenter): Presenter<RoomMemberModerationState>
}
