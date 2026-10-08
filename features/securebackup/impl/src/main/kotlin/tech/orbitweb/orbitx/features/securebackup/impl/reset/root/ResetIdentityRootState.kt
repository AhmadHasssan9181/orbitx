/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.securebackup.impl.reset.root

data class ResetIdentityRootState(
    val displayConfirmationDialog: Boolean,
    val eventSink: (ResetIdentityRootEvent) -> Unit,
)
