/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.appnav.root

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.rageshake.api.crash.aCrashDetectionState
import tech.orbitweb.orbitx.features.rageshake.api.detection.aRageshakeDetectionState
import tech.orbitweb.orbitx.services.apperror.api.AppErrorState
import tech.orbitweb.orbitx.services.apperror.api.aAppErrorState

open class RootStateProvider : PreviewParameterProvider<RootState> {
    override val values: Sequence<RootState>
        get() = sequenceOf(
            aRootState().copy(
                rageshakeDetectionState = aRageshakeDetectionState().copy(showDialog = false),
                crashDetectionState = aCrashDetectionState().copy(crashDetected = true),
            ),
            aRootState().copy(
                rageshakeDetectionState = aRageshakeDetectionState().copy(showDialog = true),
                crashDetectionState = aCrashDetectionState().copy(crashDetected = false),
            ),
            aRootState().copy(
                errorState = aAppErrorState(),
            )
        )
}

fun aRootState() = RootState(
    rageshakeDetectionState = aRageshakeDetectionState(),
    crashDetectionState = aCrashDetectionState(),
    errorState = AppErrorState.NoError,
)
