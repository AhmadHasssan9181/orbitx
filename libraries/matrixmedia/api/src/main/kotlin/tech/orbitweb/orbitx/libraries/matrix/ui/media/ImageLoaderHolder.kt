/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.media

import coil3.ImageLoader
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

interface ImageLoaderHolder {
    fun get(): ImageLoader
    fun get(client: MatrixClient): ImageLoader
    fun remove(sessionId: SessionId)
}
