/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.media.test

import coil3.Bitmap
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.matrix.ui.media.InitialsAvatarBitmapGenerator
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeInitialsAvatarBitmapGenerator(
    private val generateBitmapResult: (Int, AvatarData, Boolean, Float) -> Bitmap? = { _, _, _, _ -> lambdaError() }
) : InitialsAvatarBitmapGenerator {
    override fun generateBitmap(
        size: Int,
        avatarData: AvatarData,
        useDarkTheme: Boolean,
        fontSizePercentage: Float,
    ): Bitmap? {
        return generateBitmapResult(size, avatarData, useDarkTheme, fontSizePercentage)
    }
}
