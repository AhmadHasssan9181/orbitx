/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.di

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import tech.orbitweb.orbitx.features.home.impl.filters.RoomListFiltersPresenter
import tech.orbitweb.orbitx.features.home.impl.filters.RoomListFiltersState
import tech.orbitweb.orbitx.features.home.impl.roomlist.RoomListPresenter
import tech.orbitweb.orbitx.features.home.impl.roomlist.RoomListState
import tech.orbitweb.orbitx.features.home.impl.search.RoomListSearchPresenter
import tech.orbitweb.orbitx.features.home.impl.search.RoomListSearchState
import tech.orbitweb.orbitx.features.home.impl.spacefilters.SpaceFiltersPresenter
import tech.orbitweb.orbitx.features.home.impl.spacefilters.SpaceFiltersState
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.di.SessionScope

@ContributesTo(SessionScope::class)
@BindingContainer
interface RoomListModule {
    @Binds
    fun bindRoomListPresenter(presenter: RoomListPresenter): Presenter<RoomListState>

    @Binds
    fun bindSearchPresenter(presenter: RoomListSearchPresenter): Presenter<RoomListSearchState>

    @Binds
    fun bindFiltersPresenter(presenter: RoomListFiltersPresenter): Presenter<RoomListFiltersState>

    @Binds
    fun bindSpaceFiltersPresenter(presenter: SpaceFiltersPresenter): Presenter<SpaceFiltersState>
}
