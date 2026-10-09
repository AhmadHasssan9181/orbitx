/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.firebase

import com.google.firebase.messaging.FirebaseMessaging
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import tech.orbitweb.orbitx.libraries.core.log.logger.LoggerTag
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.pushproviders.api.Config
import tech.orbitweb.orbitx.libraries.pushproviders.api.Distributor
import tech.orbitweb.orbitx.libraries.pushproviders.api.PushProvider
import tech.orbitweb.orbitx.libraries.pushproviders.api.PusherSubscriber
import tech.orbitweb.orbitx.libraries.pushproviders.firebase.util.runFirebaseTaskWithResult
import timber.log.Timber

private val loggerTag = LoggerTag("FirebasePushProvider", LoggerTag.PushLoggerTag)

@ContributesIntoSet(AppScope::class)
class FirebasePushProvider(
    private val firebaseStore: FirebaseStore,
    private val pusherSubscriber: PusherSubscriber,
    private val isPlayServiceAvailable: IsPlayServiceAvailable,
    private val rotateFirebaseSession: RotateFirebaseSession,
    private val firebaseGatewayProvider: FirebaseGatewayProvider,
) : PushProvider {
    override val index = FirebaseConfig.INDEX
    override val name = FirebaseConfig.NAME
    override val supportMultipleDistributors = false

    override fun getDistributors(): List<Distributor> {
        return listOfNotNull(
            firebaseDistributor.takeIf { isPlayServiceAvailable.isAvailable() }
        )
    }

    override suspend fun registerWith(matrixClient: MatrixClient, distributor: Distributor): Result<Unit> {
        Timber.tag(loggerTag.value).d("Fetching fresh Firebase token from FirebaseMessaging...")
        val tokenResult = runFirebaseTaskWithResult { FirebaseMessaging.getInstance().token }
        var pushKey = tokenResult.getOrNull()
        if (!pushKey.isNullOrBlank()) {
            Timber.tag(loggerTag.value).i("Fetched Firebase token successfully: $pushKey")
            firebaseStore.storeInstallationId(pushKey)
        } else {
            Timber.tag(loggerTag.value).w(tokenResult.exceptionOrNull(), "Failed to retrieve fresh Firebase token directly, falling back to cached")
            pushKey = firebaseStore.getInstallationId()
        }

        if (pushKey.isNullOrBlank()) {
            return Result.failure<Unit>(
                IllegalStateException("Unable to register pusher, Firebase token is not known.")
            ).also {
                Timber.tag(loggerTag.value).w("Unable to register pusher, Firebase token is not known.")
            }
        }
        val gateway = firebaseGatewayProvider.getFirebaseGateway()
        Timber.tag(loggerTag.value).i("Registering pusher with gateway=$gateway, pushKey=$pushKey")
        return pusherSubscriber.registerPusher(
            matrixClient = matrixClient,
            pushKey = pushKey,
            gateway = gateway,
        )
    }

    override suspend fun getCurrentDistributorValue(sessionId: SessionId): String = firebaseDistributor.value

    override suspend fun getCurrentDistributor(sessionId: SessionId) = firebaseDistributor

    override suspend fun unregister(matrixClient: MatrixClient): Result<Unit> {
        val pushKey = firebaseStore.getInstallationId()
        return if (pushKey == null) {
            Timber.tag(loggerTag.value).w("Unable to unregister pusher, Firebase token is not known.")
            Result.success(Unit)
        } else {
            pusherSubscriber.unregisterPusher(matrixClient, pushKey, firebaseGatewayProvider.getFirebaseGateway())
        }
    }

    /**
     * Nothing to clean up here.
     */
    override suspend fun onSessionDeleted(sessionId: SessionId) = Unit

    override suspend fun getPushConfig(sessionId: SessionId): Config? {
        return firebaseStore.getInstallationId()?.let { fcmToken ->
            Config(
                url = firebaseGatewayProvider.getFirebaseGateway(),
                pushKey = fcmToken
            )
        }
    }

    override fun canRotateToken(): Boolean = true

    override suspend fun rotateToken(): Result<Unit> {
        return rotateFirebaseSession()
    }

    companion object {
        private val firebaseDistributor = Distributor("Firebase", "Firebase")
    }
}
