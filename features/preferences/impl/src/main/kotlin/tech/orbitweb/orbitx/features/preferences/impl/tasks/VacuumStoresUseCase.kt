/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.tasks

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import timber.log.Timber

fun interface VacuumStoresUseCase {
    suspend operator fun invoke()
}

@ContributesBinding(AppScope::class)
class DefaultVacuumStoresUseCase(
    private val matrixClient: MatrixClient,
) : VacuumStoresUseCase {
    override suspend fun invoke() {
        matrixClient.performDatabaseVacuum()
            .onFailure { Timber.e(it, "Failed to vacuum stores") }
    }
}
