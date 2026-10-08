/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.auth

import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

sealed class SessionRestorationException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    data class MissingSession(val sessionId: SessionId) : SessionRestorationException("Session with id $sessionId not found")
    class InvalidToken : SessionRestorationException("Access token is invalid or expired")
}
