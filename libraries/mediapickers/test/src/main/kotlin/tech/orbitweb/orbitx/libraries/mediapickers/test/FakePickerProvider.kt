/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediapickers.test

import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.runtime.Composable
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes
import tech.orbitweb.orbitx.libraries.mediapickers.api.NoOpPickerLauncher
import tech.orbitweb.orbitx.libraries.mediapickers.api.PickerLauncher
import tech.orbitweb.orbitx.libraries.mediapickers.api.PickerProvider

class FakePickerProvider : PickerProvider {
    private var mimeType = MimeTypes.Any
    private var result: Uri? = null

    @Composable
    override fun registerGalleryPicker(onResult: (uri: Uri?, mimeType: String?) -> Unit): PickerLauncher<PickVisualMediaRequest, Uri?> {
        return NoOpPickerLauncher { onResult(result, mimeType) }
    }

    @Composable
    override fun registerGalleryImagePicker(onResult: (uri: Uri?) -> Unit): PickerLauncher<PickVisualMediaRequest, Uri?> {
        return NoOpPickerLauncher { onResult(result) }
    }

    @Composable
    override fun registerGalleryMultiPicker(onResult: (uris: List<Uri>) -> Unit): PickerLauncher<PickVisualMediaRequest, List<Uri>> {
        return NoOpPickerLauncher { onResult(result?.let { listOf(it) } ?: emptyList()) }
    }

    @Composable
    override fun registerFilePicker(mimeType: String, onResult: (Uri?, String?) -> Unit): PickerLauncher<String, Uri?> {
        return NoOpPickerLauncher { onResult(result, this.mimeType) }
    }

    @Composable
    override fun registerFileMultiPicker(mimeType: String, onResult: (uris: List<Uri>) -> Unit): PickerLauncher<Array<String>, List<Uri>> {
        return NoOpPickerLauncher { onResult(result?.let { listOf(it) } ?: emptyList()) }
    }

    @Composable
    override fun registerCameraPhotoPicker(onResult: (Uri?) -> Unit): PickerLauncher<Uri, Boolean> {
        return NoOpPickerLauncher { onResult(result) }
    }

    @Composable
    override fun registerCameraVideoPicker(onResult: (Uri?) -> Unit): PickerLauncher<Uri, Boolean> {
        return NoOpPickerLauncher { onResult(result) }
    }

    fun givenResult(value: Uri?) {
        this.result = value
    }

    fun givenMimeType(mimeType: String) {
        this.mimeType = mimeType
    }
}
