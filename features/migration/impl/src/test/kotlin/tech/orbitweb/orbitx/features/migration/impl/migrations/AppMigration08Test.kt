/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl.migrations

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.features.announcement.api.Announcement
import tech.orbitweb.orbitx.features.rageshake.test.logs.FakeAnnouncementService
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value

class AppMigration08Test {
    @Test
    fun `migration on fresh install should not invoke the AnnouncementService`() = runTest {
        val service = FakeAnnouncementService(
            showAnnouncementResult = { lambdaError() },
        )
        val migration = AppMigration08(service)
        migration.migrate(isFreshInstall = true)
        assertThat(service.announcementsToShowFlow().first()).isEmpty()
    }

    @Test
    fun `migration on upgrade should invoke the AnnouncementService`() = runTest {
        val showAnnouncementResult = lambdaRecorder<Announcement, Unit> { }
        val service = FakeAnnouncementService(
            showAnnouncementResult = showAnnouncementResult,
        )
        val migration = AppMigration08(service)
        migration.migrate(isFreshInstall = false)
        showAnnouncementResult.assertions().isCalledOnce()
            .with(value(Announcement.NewNotificationSound))
    }
}
