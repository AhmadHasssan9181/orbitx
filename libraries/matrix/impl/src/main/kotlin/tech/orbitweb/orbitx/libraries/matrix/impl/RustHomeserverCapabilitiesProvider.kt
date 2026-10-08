/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl

import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.matrix.api.HomeserverCapabilitiesProvider
import org.matrix.rustcomponents.sdk.HomeserverCapabilities

class RustHomeserverCapabilitiesProvider(
    private val homeserverCapabilities: HomeserverCapabilities,
) : HomeserverCapabilitiesProvider {
    override suspend fun refresh(): Result<Unit> = runCatchingExceptions {
        homeserverCapabilities.refresh()
    }

    override suspend fun canChangeDisplayName(): Result<Boolean> = runCatchingExceptions {
        homeserverCapabilities.canChangeDisplayname()
    }

    override suspend fun canChangeAvatarUrl(): Result<Boolean> = runCatchingExceptions {
        homeserverCapabilities.canChangeAvatar()
    }
}
