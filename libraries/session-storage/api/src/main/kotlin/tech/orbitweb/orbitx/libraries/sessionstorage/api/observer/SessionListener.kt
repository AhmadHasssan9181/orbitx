/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.sessionstorage.api.observer

interface SessionListener {
    suspend fun onSessionCreated(userId: String) {}
    suspend fun onSessionDeleted(userId: String, wasLastSession: Boolean) {}
}
