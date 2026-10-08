/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class FileExtensionExtractorWithValidationTest : RobolectricTest() {
    @Test
    fun `test FileExtensionExtractor with validation OK`() {
        val sut = FileExtensionExtractorWithValidation()
        assertThat(sut.extractFromName("test.txt")).isEqualTo("txt")
    }

    @Test
    fun `test FileExtensionExtractor with validation ERROR`() {
        val sut = FileExtensionExtractorWithValidation()
        assertThat(sut.extractFromName("test.bla")).isEqualTo("bin")
    }
}
