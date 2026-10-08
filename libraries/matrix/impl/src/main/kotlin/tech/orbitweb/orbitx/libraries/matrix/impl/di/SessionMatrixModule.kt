/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.di

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.di.annotations.SessionCoroutineScope
import tech.orbitweb.orbitx.libraries.matrix.api.HomeserverCapabilitiesProvider
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.EncryptionService
import tech.orbitweb.orbitx.libraries.matrix.api.media.MatrixMediaLoader
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaPreviewService
import tech.orbitweb.orbitx.libraries.matrix.api.notificationsettings.NotificationSettingsService
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMembershipObserver
import tech.orbitweb.orbitx.libraries.matrix.api.roomdirectory.RoomDirectoryService
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.RoomListService
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceService
import tech.orbitweb.orbitx.libraries.matrix.api.sync.SyncService
import tech.orbitweb.orbitx.libraries.matrix.api.verification.SessionVerificationService
import kotlinx.coroutines.CoroutineScope

@BindingContainer
@ContributesTo(SessionScope::class)
object SessionMatrixModule {
    @Provides
    fun providesSessionId(matrixClient: MatrixClient): SessionId {
        return matrixClient.sessionId
    }

    @Provides
    fun providesSessionVerificationService(matrixClient: MatrixClient): SessionVerificationService {
        return matrixClient.sessionVerificationService
    }

    @Provides
    fun providesNotificationSettingsService(matrixClient: MatrixClient): NotificationSettingsService {
        return matrixClient.notificationSettingsService
    }

    @Provides
    fun provideRoomMembershipObserver(matrixClient: MatrixClient): RoomMembershipObserver {
        return matrixClient.roomMembershipObserver
    }

    @Provides
    fun providesRoomListService(matrixClient: MatrixClient): RoomListService {
        return matrixClient.roomListService
    }

    @Provides
    fun providesSyncService(matrixClient: MatrixClient): SyncService {
        return matrixClient.syncService
    }

    @Provides
    fun providesEncryptionService(matrixClient: MatrixClient): EncryptionService {
        return matrixClient.encryptionService
    }

    @Provides
    fun providesMatrixMediaLoader(matrixClient: MatrixClient): MatrixMediaLoader {
        return matrixClient.matrixMediaLoader
    }

    @SessionCoroutineScope
    @Provides
    fun providesSessionCoroutineScope(matrixClient: MatrixClient): CoroutineScope {
        return matrixClient.sessionCoroutineScope
    }

    @Provides
    fun providesRoomDirectoryService(matrixClient: MatrixClient): RoomDirectoryService {
        return matrixClient.roomDirectoryService
    }

    @Provides
    fun providesMediaPreviewService(matrixClient: MatrixClient): MediaPreviewService {
        return matrixClient.mediaPreviewService
    }

    @Provides
    fun providesSpaceService(matrixClient: MatrixClient): SpaceService {
        return matrixClient.spaceService
    }

    @Provides
    fun providesHomeserverCapabilitiesProvider(matrixClient: MatrixClient): HomeserverCapabilitiesProvider {
        return matrixClient.homeserverCapabilities()
    }
}
