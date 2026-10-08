/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.developer.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import tech.orbitweb.orbitx.features.preferences.impl.developer.appsettings.AppDeveloperSettingsPresenter
import tech.orbitweb.orbitx.features.preferences.impl.developer.appsettings.AppDeveloperSettingsState
import tech.orbitweb.orbitx.libraries.architecture.Presenter

@ContributesTo(AppScope::class)
@BindingContainer
interface DeveloperSettingsModule {
    @Binds
    fun bindAppDeveloperSettingsPresenter(presenter: AppDeveloperSettingsPresenter): Presenter<AppDeveloperSettingsState>
}
