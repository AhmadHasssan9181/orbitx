/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.workmanager

import tech.orbitweb.orbitx.libraries.workmanager.api.WorkManagerRequestWrapper

class FakeSyncPendingNotificationsRequestBuilder(
    private val build: () -> Result<List<WorkManagerRequestWrapper>> = { Result.success(emptyList()) },
) : SyncPendingNotificationsRequestBuilder {
    override suspend fun build(): Result<List<WorkManagerRequestWrapper>> = build.invoke()
}
