/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.firebase

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.core.extensions.flatMap
import tech.orbitweb.orbitx.libraries.core.log.logger.LoggerTag
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClientProvider
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.pushproviders.api.PusherSubscriber
import tech.orbitweb.orbitx.libraries.pushstore.api.UserPushStoreFactory
import tech.orbitweb.orbitx.libraries.sessionstorage.api.SessionStore
import tech.orbitweb.orbitx.libraries.sessionstorage.api.toUserList
import timber.log.Timber

private val loggerTag = LoggerTag("FirebaseNewInstallationIdHandler", LoggerTag.PushLoggerTag)

/**
 * Handle new installationId received from Firebase. Will update all the sessions which are using Firebase as a push provider.
 */
interface FirebaseNewInstallationIdHandler {
    suspend fun handle(installationId: String)
}

@ContributesBinding(AppScope::class)
class DefaultFirebaseNewInstallationIdHandler(
    private val pusherSubscriber: PusherSubscriber,
    private val sessionStore: SessionStore,
    private val userPushStoreFactory: UserPushStoreFactory,
    private val matrixClientProvider: MatrixClientProvider,
    private val firebaseStore: FirebaseStore,
    private val firebaseGatewayProvider: FirebaseGatewayProvider,
) : FirebaseNewInstallationIdHandler {
    override suspend fun handle(installationId: String) {
        firebaseStore.storeInstallationId(installationId)
        // Register the pusher for all the sessions
        sessionStore.getAllSessions().toUserList()
            .map { SessionId(it) }
            .forEach { sessionId ->
                val userDataStore = userPushStoreFactory.getOrCreate(sessionId)
                if (userDataStore.getPushProviderName() == FirebaseConfig.NAME) {
                    matrixClientProvider
                        .getOrRestore(sessionId)
                        .onFailure {
                            Timber.tag(loggerTag.value).e(it, "Failed to restore session $sessionId")
                        }
                        .flatMap { client ->
                            pusherSubscriber
                                .registerPusher(
                                    matrixClient = client,
                                    pushKey = installationId,
                                    gateway = firebaseGatewayProvider.getFirebaseGateway(),
                                )
                                .onFailure {
                                    Timber.tag(loggerTag.value).e(it, "Failed to register pusher for session $sessionId")
                                }
                        }
                } else {
                    Timber.tag(loggerTag.value).d("This session is not using Firebase pusher")
                }
            }
    }
}
