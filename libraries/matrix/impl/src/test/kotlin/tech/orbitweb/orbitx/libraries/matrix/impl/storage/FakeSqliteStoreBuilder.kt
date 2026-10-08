/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.storage

import tech.orbitweb.orbitx.libraries.androidutils.crypto.ClientSecret
import org.matrix.rustcomponents.sdk.ClientBuilder

class FakeSqliteStoreBuilder : SqliteStoreBuilder {
    override fun secret(clientSecret: ClientSecret?): SqliteStoreBuilder = this

    override fun setupClientBuilder(clientBuilder: ClientBuilder): ClientBuilder {
        return clientBuilder
    }
}
