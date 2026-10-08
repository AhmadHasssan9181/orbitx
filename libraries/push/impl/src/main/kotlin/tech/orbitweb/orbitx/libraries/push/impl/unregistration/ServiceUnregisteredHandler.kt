/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.unregistration

import androidx.compose.ui.graphics.toArgb
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.appconfig.NotificationConfig
import tech.orbitweb.orbitx.features.enterprise.api.EnterpriseService
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.libraries.push.impl.notifications.NotificationDisplayer
import tech.orbitweb.orbitx.libraries.push.impl.notifications.factories.NotificationAccountParams
import tech.orbitweb.orbitx.libraries.push.impl.notifications.factories.NotificationCreator
import tech.orbitweb.orbitx.libraries.sessionstorage.api.SessionStore
import kotlinx.coroutines.flow.first

interface ServiceUnregisteredHandler {
    suspend fun handle(userId: UserId)
}

@ContributesBinding(AppScope::class)
class DefaultServiceUnregisteredHandler(
    private val enterpriseService: EnterpriseService,
    private val notificationCreator: NotificationCreator,
    private val notificationDisplayer: NotificationDisplayer,
    private val sessionStore: SessionStore,
) : ServiceUnregisteredHandler {
    override suspend fun handle(userId: UserId) {
        val color = enterpriseService.brandColorsFlow(userId).first()?.toArgb()
            ?: NotificationConfig.NOTIFICATION_ACCENT_COLOR
        val hasMultipleAccounts = sessionStore.numberOfSessions() > 1
        val notification = notificationCreator.createUnregistrationNotification(
            NotificationAccountParams(
                user = MatrixUser(userId),
                color = color,
                showSessionId = hasMultipleAccounts,
            )
        )
        notificationDisplayer.displayUnregistrationNotification(notification)
    }
}
