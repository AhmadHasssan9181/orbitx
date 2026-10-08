/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.firebase

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions

fun interface RotateFirebaseSession {
    suspend operator fun invoke(): Result<Unit>
}

/**
 * This class deletes the Firebase installation id and generates a new one.
 */
@ContributesBinding(AppScope::class)
class DefaultRotateFirebaseSession(
    private val registerFirebaseSession: RegisterFirebaseSession,
    private val unregisterFirebaseSession: UnregisterFirebaseSession,
) : RotateFirebaseSession {
    override suspend operator fun invoke(): Result<Unit> {
        return runCatchingExceptions {
            // Stop the current session, which will also delete the existing installation id from Firebase
            unregisterFirebaseSession().getOrThrow()
            // Register again to get a new installation id
            registerFirebaseSession().getOrThrow()
        }
    }
}
