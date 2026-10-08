/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.appnav.root

import tech.orbitweb.orbitx.features.rageshake.api.crash.CrashDetectionState
import tech.orbitweb.orbitx.features.rageshake.api.detection.RageshakeDetectionState
import tech.orbitweb.orbitx.services.apperror.api.AppErrorState

data class RootState(
    val rageshakeDetectionState: RageshakeDetectionState,
    val crashDetectionState: CrashDetectionState,
    val errorState: AppErrorState,
)
