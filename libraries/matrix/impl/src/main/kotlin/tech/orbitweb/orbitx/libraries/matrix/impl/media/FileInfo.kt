/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.media

import tech.orbitweb.orbitx.libraries.matrix.api.media.FileInfo
import org.matrix.rustcomponents.sdk.FileInfo as RustFileInfo

fun RustFileInfo.map(): FileInfo = FileInfo(
    mimetype = mimetype,
    size = size?.toLong(),
    thumbnailInfo = thumbnailInfo?.map(),
    thumbnailSource = thumbnailSource?.map()
)

fun FileInfo.map(): RustFileInfo = RustFileInfo(
    mimetype = mimetype,
    size = size?.toULong(),
    thumbnailInfo = thumbnailInfo?.map(),
    thumbnailSource = null
)
