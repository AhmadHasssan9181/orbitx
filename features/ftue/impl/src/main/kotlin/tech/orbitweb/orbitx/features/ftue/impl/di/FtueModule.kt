/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.ftue.impl.di

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import tech.orbitweb.orbitx.features.ftue.impl.sessionverification.choosemode.ChooseSelfVerificationModePresenter
import tech.orbitweb.orbitx.features.ftue.impl.sessionverification.choosemode.ChooseSelfVerificationModeState
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.di.SessionScope

@ContributesTo(SessionScope::class)
@BindingContainer
interface FtueModule {
    @Binds
    fun bindChooseSelfVerificationMethodPresenter(presenter: ChooseSelfVerificationModePresenter): Presenter<ChooseSelfVerificationModeState>
}
