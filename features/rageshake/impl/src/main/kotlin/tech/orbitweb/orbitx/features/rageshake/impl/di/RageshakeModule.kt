/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.impl.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import tech.orbitweb.orbitx.features.rageshake.api.crash.CrashDetectionPresenter
import tech.orbitweb.orbitx.features.rageshake.api.crash.CrashDetectionState
import tech.orbitweb.orbitx.features.rageshake.api.detection.RageshakeDetectionPresenter
import tech.orbitweb.orbitx.features.rageshake.api.detection.RageshakeDetectionState
import tech.orbitweb.orbitx.features.rageshake.api.preferences.RageshakePreferencesPresenter
import tech.orbitweb.orbitx.features.rageshake.api.preferences.RageshakePreferencesState
import tech.orbitweb.orbitx.libraries.architecture.Presenter

@ContributesTo(AppScope::class)
@BindingContainer
interface RageshakeModule {
    @Binds
    fun bindRageshakePreferencesPresenter(presenter: RageshakePreferencesPresenter): Presenter<RageshakePreferencesState>

    @Binds
    fun bindRageshakeDetectionPresenter(presenter: RageshakeDetectionPresenter): Presenter<RageshakeDetectionState>

    @Binds
    fun bindCrashDetectionPresenter(presenter: CrashDetectionPresenter): Presenter<CrashDetectionState>
}
