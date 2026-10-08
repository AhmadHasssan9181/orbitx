/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.classic.loginwithclassic

import android.graphics.Bitmap
import androidx.compose.runtime.Stable
import tech.orbitweb.orbitx.features.login.impl.login.LoginMode
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId

@Stable
data class LoginWithClassicState(
    val isElementPro: Boolean,
    val userId: UserId,
    val displayName: String?,
    val avatar: Bitmap?,
    val loginWithClassicAction: AsyncAction<Unit>,
    val loginMode: AsyncData<LoginMode>,
    val eventSink: (LoginWithClassicEvent) -> Unit,
)
