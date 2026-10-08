/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl.share

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.features.location.impl.common.ui.LocationConstraintsDialogState
import tech.orbitweb.orbitx.features.location.impl.common.userlocation.UserLocationState
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

data class ShareLocationState(
    val customMapStyleUrl: AsyncData<String?>,
    val currentUser: MatrixUser,
    val dialogState: Dialog,
    val trackUserLocation: Boolean,
    val userLocationState: UserLocationState,
    val appName: String,
    val canShareLiveLocation: Boolean,
    val startLiveLocationAction: AsyncAction<Unit>,
    val eventSink: (ShareLocationEvent) -> Unit,
) {
    sealed interface Dialog {
        data object None : Dialog
        data class Constraints(val state: LocationConstraintsDialogState) : Dialog
        data object LiveLocationDisclaimer : Dialog
        data class LiveLocationDurations(val durations: ImmutableList<LiveLocationDuration>) : Dialog
    }
}
