/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.impl.ui

import tech.orbitweb.orbitx.libraries.architecture.AsyncData

data class CallScreenState(
    val urlState: AsyncData<String>,
    val webViewError: String?,
    val userAgent: String,
    val isCallActive: Boolean,
    val eventSink: (CallScreenEvent) -> Unit,
)
