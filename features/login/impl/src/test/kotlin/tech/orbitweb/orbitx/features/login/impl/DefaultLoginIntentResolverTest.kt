/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.features.login.api.LoginParams
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class DefaultLoginIntentResolverTest : RobolectricTest() {
    @Test
    fun `nominal case`() {
        val sut = DefaultLoginIntentResolver()
        val uriString = "https://mobile.element.io/element/?account_provider=example.org&login_hint=mxid:@alice:example.org"
        assertThat(sut.parse(uriString)).isEqualTo(
            LoginParams(
                accountProvider = "example.org",
                loginHint = "mxid:@alice:example.org",
            )
        )
    }

    @Test
    fun `extra unknown param`() {
        val sut = DefaultLoginIntentResolver()
        val uriString = "https://mobile.element.io/element/?account_provider=example.org&login_hint=mxid:@alice:example.org&extra=uknown"
        assertThat(sut.parse(uriString)).isEqualTo(
            LoginParams(
                accountProvider = "example.org",
                loginHint = "mxid:@alice:example.org",
            )
        )
    }

    @Test
    fun `no account provider`() {
        val sut = DefaultLoginIntentResolver()
        val uriString = "https://mobile.element.io/element/?login_hint=mxid:@alice:example.org"
        assertThat(sut.parse(uriString)).isNull()
    }

    @Test
    fun `no path`() {
        val sut = DefaultLoginIntentResolver()
        val uriString = "https://mobile.element.io?account_provider=example.org&login_hint=mxid:@alice:example.org"
        assertThat(sut.parse(uriString)).isNull()
    }

    @Test
    fun `wrong path`() {
        val sut = DefaultLoginIntentResolver()
        val uriString = "https://mobile.element.io/wrong?account_provider=example.org&login_hint=mxid:@alice:example.org"
        assertThat(sut.parse(uriString)).isNull()
    }

    @Test
    fun `wrong host`() {
        val sut = DefaultLoginIntentResolver()
        val uriString = "https://wrong.element.io/element/?account_provider=example.org&login_hint=mxid:@alice:example.org"
        assertThat(sut.parse(uriString)).isNull()
    }

    @Test
    fun `no login_hint param`() {
        val sut = DefaultLoginIntentResolver()
        val uriString = "https://mobile.element.io/element/?account_provider=example.org"
        assertThat(sut.parse(uriString)).isEqualTo(
            LoginParams(
                accountProvider = "example.org",
                loginHint = null,
            )
        )
    }
}
