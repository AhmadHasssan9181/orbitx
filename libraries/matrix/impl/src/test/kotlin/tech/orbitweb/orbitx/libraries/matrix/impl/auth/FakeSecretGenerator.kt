/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.auth

import tech.orbitweb.orbitx.libraries.androidutils.crypto.ClientSecret
import tech.orbitweb.orbitx.libraries.matrix.impl.keys.SecretGenerator
import tech.orbitweb.orbitx.libraries.matrix.test.A_PASSPHRASE

class FakeSecretGenerator(
    private val passphrase: (Int) -> String? = { A_PASSPHRASE },
    private val key: (Int) -> ByteArray = { ByteArray(it) { 0 } },
) : SecretGenerator {
    override fun generatePassphrase(size: Int): ClientSecret.Passphrase? = passphrase(size)?.let { ClientSecret.Passphrase(it) }
    override fun generateKey(size: Int): ClientSecret.RawKey = ClientSecret.RawKey(key(size))
}
