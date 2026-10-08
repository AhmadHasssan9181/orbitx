/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.di

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import kotlinx.coroutines.CoroutineScope
import tech.orbitweb.orbitx.libraries.di.RoomScope
import tech.orbitweb.orbitx.libraries.di.annotations.RoomCoroutineScope
import tech.orbitweb.orbitx.libraries.matrix.api.room.BaseRoom

@BindingContainer
@ContributesTo(RoomScope::class)
object RoomModule {
    @RoomCoroutineScope
    @Provides
    fun providesSessionCoroutineScope(room: BaseRoom): CoroutineScope {
        return room.roomCoroutineScope
    }
}
