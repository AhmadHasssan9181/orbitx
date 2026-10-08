/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.signedout.impl

import tech.orbitweb.orbitx.libraries.sessionstorage.api.SessionData

data class SignedOutState(
    val appName: String,
    val signedOutSession: SessionData?,
    val eventSink: (SignedOutEvents) -> Unit,
)
