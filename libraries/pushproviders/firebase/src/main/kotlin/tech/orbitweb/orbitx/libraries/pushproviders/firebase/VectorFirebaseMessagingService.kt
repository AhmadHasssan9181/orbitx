/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.RemoteMessage.PRIORITY_HIGH
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tech.orbitweb.orbitx.libraries.architecture.bindings
import tech.orbitweb.orbitx.libraries.core.log.logger.LoggerTag
import tech.orbitweb.orbitx.libraries.di.annotations.AppCoroutineScope
import tech.orbitweb.orbitx.libraries.push.api.push.FetchPushForegroundServiceManager
import tech.orbitweb.orbitx.libraries.pushproviders.api.PushHandler
import timber.log.Timber

private val loggerTag = LoggerTag("VectorFirebaseMessagingService", LoggerTag.PushLoggerTag)

class VectorFirebaseMessagingService : FirebaseMessagingService() {
    @Inject lateinit var firebaseNewInstallationIdHandler: FirebaseNewInstallationIdHandler
    @Inject lateinit var pushParser: FirebasePushParser
    @Inject lateinit var pushHandler: PushHandler
    @Inject lateinit var fetchPushForegroundServiceManager: FetchPushForegroundServiceManager
    @AppCoroutineScope
    @Inject lateinit var coroutineScope: CoroutineScope

    override fun onCreate() {
        super.onCreate()
        bindings<VectorFirebaseMessagingServiceBindings>().inject(this)
    }

    override fun onRegistered(installationId: String) {
        super.onRegistered(installationId)

        Timber.tag(loggerTag.value).w("New Firebase installation id")
        coroutineScope.launch {
            firebaseNewInstallationIdHandler.handle(installationId)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Timber.tag(loggerTag.value).w("New Firebase message. Priority: ${message.priority}/${message.originalPriority}")

        val isHighPriority = message.priority == PRIORITY_HIGH
        if (isHighPriority) {
            // Acquire wakelock to ensure the device stays awake while we handle the push and schedule and run the work
            fetchPushForegroundServiceManager.start()
        }

        coroutineScope.launch {
            val pushData = pushParser.parse(message.data)
            if (pushData == null) {
                Timber.tag(loggerTag.value).w("Invalid data received from Firebase")
                pushHandler.handleInvalid(
                    providerInfo = FirebaseConfig.NAME,
                    data = message.data.keys.joinToString("\n") {
                        "$it: ${message.data[it]}"
                    },
                )
                if (isHighPriority) {
                    fetchPushForegroundServiceManager.stop()
                }
            } else {
                val handled = pushHandler.handle(
                    pushData = pushData,
                    providerInfo = FirebaseConfig.NAME,
                )

                // If we failed to handle the push, we should release the wakelock early to avoid keeping the device awake for too long.
                if (!handled && isHighPriority) {
                    fetchPushForegroundServiceManager.stop()
                }
            }
        }
    }
}
