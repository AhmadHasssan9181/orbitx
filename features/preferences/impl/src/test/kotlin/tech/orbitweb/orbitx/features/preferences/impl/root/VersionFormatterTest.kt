/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.root

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.test.core.aBuildMeta
import tech.orbitweb.orbitx.services.toolbox.test.strings.FakeStringProvider

class VersionFormatterTest {
    @Test
    fun `version formatter should return simplified version for main branch`() = runTest {
        val sut = DefaultVersionFormatter(
            stringProvider = FakeStringProvider(defaultResult = VERSION),
            buildMeta = aBuildMeta(
                gitBranchName = "main",
                versionName = "versionName",
                versionCode = 123
            )
        )
        assertThat(sut.get()).isEqualTo("${VERSION}versionName, 123")
    }

    @Test
    fun `version formatter should return simplified version for other branch`() = runTest {
        val sut = DefaultVersionFormatter(
            stringProvider = FakeStringProvider(defaultResult = VERSION),
            buildMeta = aBuildMeta(
                versionName = "versionName",
                versionCode = 123,
                gitBranchName = "branch",
                gitRevision = "1234567890",
            )
        )
        assertThat(sut.get()).isEqualTo("${VERSION}versionName, 123\nbranch (1234567890)")
    }

    companion object {
        const val VERSION = "version"
    }
}
