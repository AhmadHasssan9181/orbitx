/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.error

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.features.login.impl.R
import tech.orbitweb.orbitx.libraries.matrix.api.auth.AuthenticationException
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

class ErrorFormatterTest {
    // region loginError
    @Test
    fun `loginError - invalid unknown error returns unknown error message`() {
        val error = RuntimeException("Some unknown error")
        assertThat(loginError(error)).isEqualTo(CommonStrings.error_unknown)
    }

    @Test
    fun `loginError - invalid auth error returns unknown error message`() {
        val error = AuthenticationException.SlidingSyncVersion("Some message. Also contains M_FORBIDDEN, but won't be parsed")
        assertThat(loginError(error)).isEqualTo(CommonStrings.error_unknown)
    }

    @Test
    fun `loginError - unknown error returns unknown error message`() {
        val error = AuthenticationException.Generic("M_UNKNOWN")
        assertThat(loginError(error)).isEqualTo(CommonStrings.error_unknown)
    }

    @Test
    fun `loginError - forbidden error returns incorrect credentials message`() {
        val error = AuthenticationException.Generic("M_FORBIDDEN")
        assertThat(loginError(error)).isEqualTo(R.string.screen_login_error_invalid_credentials)
    }

    @Test
    fun `loginError - user_deactivated error returns deactivated account message`() {
        val error = AuthenticationException.Generic("M_USER_DEACTIVATED")
        assertThat(loginError(error)).isEqualTo(R.string.screen_login_error_deactivated_account)
    }

    // endregion loginError
}
