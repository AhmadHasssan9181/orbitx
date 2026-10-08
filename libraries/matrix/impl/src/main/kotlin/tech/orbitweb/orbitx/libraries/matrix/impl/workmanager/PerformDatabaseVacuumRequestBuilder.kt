/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.workmanager

import androidx.work.Constraints
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.impl.workmanager.VacuumDatabaseWorker.Companion.SESSION_ID_PARAM
import tech.orbitweb.orbitx.libraries.workmanager.api.WorkManagerRequestBuilder
import tech.orbitweb.orbitx.libraries.workmanager.api.WorkManagerRequestType
import tech.orbitweb.orbitx.libraries.workmanager.api.WorkManagerRequestWrapper
import tech.orbitweb.orbitx.libraries.workmanager.api.workManagerTag
import java.util.concurrent.TimeUnit

class PerformDatabaseVacuumRequestBuilder(
    private val sessionId: SessionId,
) : WorkManagerRequestBuilder {
    override suspend fun build(): Result<List<WorkManagerRequestWrapper>> {
        val data = Data.Builder().putString(SESSION_ID_PARAM, sessionId.value).build()
        val workRequest = PeriodicWorkRequest.Builder(
            workerClass = VacuumDatabaseWorker::class,
            // Run once a day
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.DAYS,
        )
            .addTag(workManagerTag(sessionId, WorkManagerRequestType.DB_VACUUM))
            .setInputData(data)
            // Only run when the device is idle to avoid impacting user experience
            .setConstraints(
                Constraints.Builder()
                    .setRequiresDeviceIdle(true)
                    // Vacuuming can duplicate the DB sizes in disk
                    .setRequiresStorageNotLow(true)
                    .build()
            )
            .build()

        return Result.success(listOf(WorkManagerRequestWrapper(workRequest)))
    }
}
