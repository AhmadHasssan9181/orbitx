/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediapickers.api

import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.runtime.Composable

interface PickerProvider {
    @Composable
    fun registerGalleryPicker(
        onResult: (uri: Uri?, mimeType: String?) -> Unit
    ): PickerLauncher<PickVisualMediaRequest, Uri?>

    @Composable
    fun registerGalleryImagePicker(
        onResult: (Uri?) -> Unit
    ): PickerLauncher<PickVisualMediaRequest, Uri?>

    @Composable
    fun registerGalleryMultiPicker(
        onResult: (uris: List<Uri>) -> Unit
    ): PickerLauncher<PickVisualMediaRequest, List<Uri>>

    @Composable
    fun registerFilePicker(
        mimeType: String,
        onResult: (uri: Uri?, mimeType: String?) -> Unit,
    ): PickerLauncher<String, Uri?>

    @Composable
    fun registerFileMultiPicker(
        mimeType: String,
        onResult: (uris: List<Uri>) -> Unit,
    ): PickerLauncher<Array<String>, List<Uri>>

    @Composable
    fun registerCameraPhotoPicker(onResult: (Uri?) -> Unit): PickerLauncher<Uri, Boolean>

    @Composable
    fun registerCameraVideoPicker(onResult: (Uri?) -> Unit): PickerLauncher<Uri, Boolean>
}
