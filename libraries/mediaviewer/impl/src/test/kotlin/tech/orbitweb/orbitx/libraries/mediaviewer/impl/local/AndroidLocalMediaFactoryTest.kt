/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.local

import android.graphics.Bitmap
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.robolectric.RuntimeEnvironment
import tech.orbitweb.orbitx.libraries.androidutils.file.getMimeType
import tech.orbitweb.orbitx.libraries.androidutils.filesize.FakeFileSizeFormatter
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaFile
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_NAME
import tech.orbitweb.orbitx.libraries.matrix.test.media.FakeMediaFile
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.api.anImageMediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.test.util.FileExtensionExtractorWithoutValidation
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import java.io.File
import java.io.FileOutputStream

class AndroidLocalMediaFactoryTest : RobolectricTest() {
    private val context = RuntimeEnvironment.getApplication()

    @Test
    fun `test AndroidLocalMediaFactory`() {
        val sut = createAndroidLocalMediaFactory()
        val result = sut.createFromMediaFile(
            mediaFile = aMediaFile(),
            mediaInfo = anImageMediaInfo(
                senderId = A_USER_ID,
                senderName = A_USER_NAME,
                dateSent = "12:34",
                dateSentFull = "full",
            )
        )
        assertThat(result.uri.toString()).endsWith("aPath")
        assertThat(result.info).isEqualTo(
            MediaInfo(
                filename = "an image file.jpg",
                // MediaFile does not provide file size in this test
                fileSize = 0L,
                caption = null,
                mimeType = MimeTypes.Jpeg,
                formattedFileSize = "4MB",
                fileExtension = "jpg",
                senderId = A_USER_ID,
                senderName = A_USER_NAME,
                senderAvatar = null,
                dateSent = "12:34",
                dateSentFull = "full",
                waveform = null,
                duration = null,
            )
        )
    }

    @Test
    fun `createFromUri detects image mime type from content when picker mime type is generic`() {
        val imageFile = File(context.cacheDir, "picked-media").apply {
            FileOutputStream(this).use { outputStream ->
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
                    .compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }
        }

        val result = createAndroidLocalMediaFactory().createFromUri(
            uri = imageFile.toURI().toString().let(android.net.Uri::parse),
            mimeType = MimeTypes.OctetStream,
            name = imageFile.name,
            formattedFileSize = null,
        )

        assertThat(context.getMimeType(result.uri)).isNull()
        assertThat(result.info.mimeType).isEqualTo(MimeTypes.Png)
        assertThat(result.info.fileExtension).isEmpty()
    }

    private fun aMediaFile(): MediaFile {
        return FakeMediaFile("aPath")
    }

    private fun createAndroidLocalMediaFactory(): AndroidLocalMediaFactory {
        return AndroidLocalMediaFactory(
            context,
            FakeFileSizeFormatter(),
            FileExtensionExtractorWithoutValidation()
        )
    }
}
