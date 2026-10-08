/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.createaccount

import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

data class CreateAccountState(
    val url: String,
    val pageProgress: Int,
    val createAction: AsyncAction<SessionId>,
    val isDebugBuild: Boolean,
    val eventSink: (CreateAccountEvents) -> Unit
)
