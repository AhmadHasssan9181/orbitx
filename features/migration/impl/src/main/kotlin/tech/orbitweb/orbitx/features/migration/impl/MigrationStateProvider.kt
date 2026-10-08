/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.api.MigrationState
import tech.orbitweb.orbitx.libraries.architecture.AsyncData

internal class MigrationStateProvider : PreviewParameterProvider<MigrationState> {
    override val values: Sequence<MigrationState>
        get() = sequenceOf(
            aMigrationState(),
            aMigrationState(migrationAction = AsyncData.Loading(Unit)),
        )
}

internal fun aMigrationState(
    migrationAction: AsyncData<Unit> = AsyncData.Uninitialized,
) = MigrationState(
    migrationAction = migrationAction,
)
