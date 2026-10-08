/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api

/**
 * Provides information about the capabilities of the homeserver.
 *
 * Spec: https://spec.matrix.org/latest/client-server-api/#capabilities-negotiation
 */
interface HomeserverCapabilitiesProvider {
    /**
     * Manually refresh the capabilities of the homeserver performing a network request.
     */
    suspend fun refresh(): Result<Unit>

    /**
     * Indicates whether the homeserver allows the user to change their display name.
     */
    suspend fun canChangeDisplayName(): Result<Boolean>

    /**
     * Indicates whether the homeserver allows the user to change their avatar URL.
     */
    suspend fun canChangeAvatarUrl(): Result<Boolean>
}
