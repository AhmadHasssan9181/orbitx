/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.classic.missingkeybackup

import androidx.compose.runtime.Composable
import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta

@Inject
class MissingKeyBackupPresenter(
    private val buildMeta: BuildMeta,
) : Presenter<MissingKeyBackupState> {
    @Composable
    override fun present(): MissingKeyBackupState {
        return MissingKeyBackupState(
            appName = buildMeta.applicationName,
        )
    }
}
