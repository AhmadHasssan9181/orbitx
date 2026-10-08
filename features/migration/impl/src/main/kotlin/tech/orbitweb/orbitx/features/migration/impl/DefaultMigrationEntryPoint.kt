/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.api.MigrationEntryPoint
import tech.orbitweb.orbitx.features.api.MigrationState

@ContributesBinding(AppScope::class)
class DefaultMigrationEntryPoint(
    private val migrationPresenter: MigrationPresenter,
) : MigrationEntryPoint {
    @Composable
    override fun present(): MigrationState = migrationPresenter.present()

    @Composable
    override fun Render(
        state: MigrationState,
        modifier: Modifier,
    ) = MigrationView(
        migrationState = state,
        modifier = modifier,
    )
}
