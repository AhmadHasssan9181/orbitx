/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.firebase

import com.google.firebase.messaging.FirebaseMessaging
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.pushproviders.firebase.util.runFirebaseTaskWithResult
import timber.log.Timber

fun interface RegisterFirebaseSession {
    /**
     * Register the device to Firebase Messaging.
     */
    suspend operator fun invoke(): Result<Unit>
}

@ContributesBinding(AppScope::class)
class DefaultRegisterFirebaseSession(
    private val isPlayServiceAvailable: IsPlayServiceAvailable,
    private val firebaseNewInstallationIdHandler: FirebaseNewInstallationIdHandler,
) : RegisterFirebaseSession {
    override suspend operator fun invoke(): Result<Unit> {
        // 'app should always check the device for a compatible Google Play services APK before accessing Google Play services features'
        isPlayServiceAvailable.checkAvailableOrThrow()
        return runFirebaseTaskWithResult { FirebaseMessaging.getInstance().token }
            .onSuccess { token ->
                Timber.d("Retrieved Firebase token during registration: $token")
                firebaseNewInstallationIdHandler.handle(token)
            }
            .onFailure { Timber.e(it, "## registerFirebaseMessaging() : failed") }
            .map {}
    }
}
