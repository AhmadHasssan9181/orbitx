/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.media

import android.graphics.Bitmap
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData

/**
 * Generates a bitmap for an initials avatar based on the provided [tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData].
 */
interface InitialsAvatarBitmapGenerator {
    fun generateBitmap(
        size: Int,
        avatarData: AvatarData,
        useDarkTheme: Boolean,
        fontSizePercentage: Float = 0.5f,
    ): Bitmap?
}
