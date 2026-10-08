/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediapickers

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes
import tech.orbitweb.orbitx.libraries.mediapickers.api.PickerType
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class PickerTypeTest : RobolectricTest() {
    @Test
    fun `ImageAndVideo - assert types`() {
        val pickerType = PickerType.ImageAndVideo
        assertThat(pickerType.getContract()).isInstanceOf(ActivityResultContracts.PickVisualMedia::class.java)
        assertThat(pickerType.getDefaultRequest().mediaType).isEqualTo(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
    }

    @Test
    fun `File - assert types`() {
        val pickerType = PickerType.File()
        assertThat(pickerType.getContract()).isInstanceOf(ActivityResultContracts.GetContent::class.java)
        assertThat(pickerType.getDefaultRequest()).isEqualTo(MimeTypes.Any)

        val mimeType = MimeTypes.Images
        val customPickerType = PickerType.File(mimeType)
        assertThat(customPickerType.getContract()).isInstanceOf(ActivityResultContracts.GetContent::class.java)
        assertThat(customPickerType.getDefaultRequest()).isEqualTo(mimeType)
    }

    @Test
    fun `CameraPhoto - assert types`() {
        val uri = Uri.parse("file:///tmp/test")
        val pickerType = PickerType.Camera.Photo(uri)
        assertThat(pickerType.getContract()).isInstanceOf(ActivityResultContracts.TakePicture::class.java)
        assertThat(pickerType.getDefaultRequest()).isEqualTo(uri)
    }

    @Test
    fun `CameraVideo - assert types`() {
        val uri = Uri.parse("file:///tmp/test")
        val pickerType = PickerType.Camera.Video(uri)
        assertThat(pickerType.getContract()).isInstanceOf(ActivityResultContracts.CaptureVideo::class.java)
        assertThat(pickerType.getDefaultRequest()).isEqualTo(uri)
    }
}
