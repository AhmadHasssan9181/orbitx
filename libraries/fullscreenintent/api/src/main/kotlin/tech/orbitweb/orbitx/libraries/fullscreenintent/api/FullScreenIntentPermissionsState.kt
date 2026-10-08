/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.fullscreenintent.api

data class FullScreenIntentPermissionsState(
    val permissionGranted: Boolean,
    val shouldDisplayBanner: Boolean,
    val eventSink: (FullScreenIntentPermissionsEvents) -> Unit,
)
