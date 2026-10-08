/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.classic.loginwithclassic

import android.graphics.Bitmap
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.login.impl.login.LoginMode
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_ALICE
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId

open class LoginWithClassicStateProvider : PreviewParameterProvider<LoginWithClassicState> {
    override val values: Sequence<LoginWithClassicState>
        get() = sequenceOf(
            aLoginWithClassicState(),
            aLoginWithClassicState(isElementPro = true, displayName = USER_NAME_ALICE),
        )
}

fun aLoginWithClassicState(
    isElementPro: Boolean = false,
    userId: UserId = UserId("@alice:matrix.org"),
    displayName: String? = null,
    avatar: Bitmap? = null,
    loginWithClassicAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
    loginMode: AsyncData<LoginMode> = AsyncData.Uninitialized,
    eventSink: (LoginWithClassicEvent) -> Unit = {},
) = LoginWithClassicState(
    isElementPro = isElementPro,
    userId = userId,
    displayName = displayName,
    avatar = avatar,
    loginWithClassicAction = loginWithClassicAction,
    loginMode = loginMode,
    eventSink = eventSink,
)
