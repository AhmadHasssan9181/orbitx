/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import tech.orbitweb.orbitx.features.login.impl.changeserver.ChangeServerPresenter
import tech.orbitweb.orbitx.features.login.impl.changeserver.ChangeServerState
import tech.orbitweb.orbitx.libraries.architecture.Presenter

@ContributesTo(AppScope::class)
@BindingContainer
interface LoginModule {
    @Binds
    fun bindChangeServerPresenter(presenter: ChangeServerPresenter): Presenter<ChangeServerState>
}
