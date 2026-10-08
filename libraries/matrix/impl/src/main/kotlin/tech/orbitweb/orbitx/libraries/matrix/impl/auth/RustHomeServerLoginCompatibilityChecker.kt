/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.auth

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.matrix.api.auth.HomeServerLoginCompatibilityChecker
import tech.orbitweb.orbitx.libraries.matrix.impl.ClientBuilderProvider
import timber.log.Timber

@ContributesBinding(AppScope::class)
class RustHomeServerLoginCompatibilityChecker(
    private val clientBuilderProvider: ClientBuilderProvider,
    ) : HomeServerLoginCompatibilityChecker {
    override suspend fun check(url: String): Result<Boolean> = runCatchingExceptions {
        clientBuilderProvider.provide()
            .inMemoryStore()
            .serverNameOrHomeserverUrl(url)
            .build()
            .use {
                it.homeserverLoginDetails()
            }
            .use {
                Timber.d("Homeserver $url | OAuth: ${it.supportsOauthLogin()} | Password: ${it.supportsPasswordLogin()} | SSO: ${it.supportsSsoLogin()}")
                it.supportsOauthLogin() || it.supportsPasswordLogin()
            }
    }
}
