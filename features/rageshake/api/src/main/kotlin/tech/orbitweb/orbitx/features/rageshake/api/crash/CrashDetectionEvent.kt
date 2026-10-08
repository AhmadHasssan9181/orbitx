/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.api.crash

sealed interface CrashDetectionEvent {
    data object ResetAllCrashData : CrashDetectionEvent
    data object ResetAppHasCrashed : CrashDetectionEvent
}
