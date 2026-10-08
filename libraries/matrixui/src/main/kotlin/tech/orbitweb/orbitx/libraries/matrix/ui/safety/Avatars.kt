/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.safety

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import tech.orbitweb.orbitx.libraries.core.coroutine.mapState
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient

@Composable
fun MatrixClient.rememberHideInvitesAvatar(): State<Boolean> {
    return remember {
        mediaPreviewService
            .mediaPreviewConfigFlow
            .mapState { config -> config.hideInviteAvatar }
    }.collectAsState()
}
