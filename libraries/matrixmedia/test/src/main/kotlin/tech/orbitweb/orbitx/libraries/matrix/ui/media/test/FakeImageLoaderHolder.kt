/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.media.test

import coil3.ImageLoader
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.ui.media.ImageLoaderHolder

class FakeImageLoaderHolder(
    val fakeImageLoader: ImageLoader = FakeImageLoader(),
) : ImageLoaderHolder {
    override fun get(): ImageLoader {
        return fakeImageLoader
    }

    override fun get(client: MatrixClient): ImageLoader {
        return fakeImageLoader
    }

    override fun remove(sessionId: SessionId) {
        // No-op
    }
}
