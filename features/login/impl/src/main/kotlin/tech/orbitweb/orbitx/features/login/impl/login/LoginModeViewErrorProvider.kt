/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.login

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.login.impl.error.ChangeServerErrorProvider
import tech.orbitweb.orbitx.libraries.matrix.api.auth.AuthenticationException

class LoginModeViewErrorProvider : PreviewParameterProvider<Exception> {
    override val values: Sequence<Exception>
        get() = ChangeServerErrorProvider().values +
            AuthenticationException.AccountAlreadyLoggedIn("@alice:matrix.org")
}
