/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.tests.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withImportNamed
import com.lemonappdev.konsist.api.verify.assertFalse
import org.junit.Test

class KonsistContentTest {
    @Test
    fun `assert that BuildConfig dot VersionCode is not used`() {
        Konsist
            .scopeFromProduction()
            .files
            .withImportNamed("tech.orbitweb.orbitx.BuildConfig")
            .assertFalse(additionalMessage = "Please do not use BuildConfig.VERSION_CODE, but use the versionCode from BuildMeta") {
                it.text.contains("BuildConfig.VERSION_CODE")
            }
    }
}
