/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.impl.screenshot

import android.graphics.Bitmap

const val A_SCREENSHOT_URI = "file://content/uri"

class FakeScreenshotHolder(private val screenshotUri: String? = null) : ScreenshotHolder {
    override fun writeBitmap(data: Bitmap) = Unit

    override fun getFileUri() = screenshotUri

    override fun reset() = Unit
}
