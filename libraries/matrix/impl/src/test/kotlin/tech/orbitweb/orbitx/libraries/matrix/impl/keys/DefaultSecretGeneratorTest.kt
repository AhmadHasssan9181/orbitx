/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.keys

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.libraries.androidutils.crypto.ClientSecret
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test

class DefaultSecretGeneratorTest : RobolectricTest() {
    @Test
    fun `check that generated passphrase has the expected length`() {
        val secretGenerator = DefaultSecretGenerator()
        val passphrase = secretGenerator.generatePassphrase(256)
        assertThat(passphrase).isInstanceOf(ClientSecret.Passphrase::class.java)
        // Size after Base64 encoding should be 4/3 of the original size, without padding
        assertThat(passphrase!!.value).hasLength(342)
    }

    @Test
    fun `check that generated key has the expected length`() {
        val secretGenerator = DefaultSecretGenerator()
        val key = secretGenerator.generateKey(123)
        assertThat(key).isInstanceOf(ClientSecret.RawKey::class.java)
        assertThat(key.bytes).hasLength(123)
    }
}
