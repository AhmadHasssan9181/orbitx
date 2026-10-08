/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.error

import androidx.annotation.StringRes
import tech.orbitweb.orbitx.features.login.impl.R
import tech.orbitweb.orbitx.libraries.matrix.api.auth.AuthErrorCode
import tech.orbitweb.orbitx.libraries.matrix.api.auth.AuthenticationException
import tech.orbitweb.orbitx.libraries.matrix.api.auth.errorCode
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@StringRes
fun loginError(
    throwable: Throwable
): Int {
    val authException = throwable as? AuthenticationException ?: return CommonStrings.error_unknown
    return when (authException.errorCode) {
        AuthErrorCode.FORBIDDEN -> R.string.screen_login_error_invalid_credentials
        AuthErrorCode.USER_DEACTIVATED -> R.string.screen_login_error_deactivated_account
        AuthErrorCode.UNKNOWN -> CommonStrings.error_unknown
    }
}
