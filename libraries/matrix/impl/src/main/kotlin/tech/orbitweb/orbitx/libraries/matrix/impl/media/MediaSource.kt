/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.media

import org.matrix.rustcomponents.sdk.use
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource
import org.matrix.rustcomponents.sdk.MediaSource as RustMediaSource

fun RustMediaSource.map(): MediaSource = use {
    MediaSource(it.url(), it.toJson())
}
