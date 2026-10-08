/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.tests.konsist

import com.google.common.truth.Truth.assertThat
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class KonsistLicenseTest {
    private val publicLicense = """
        /\*
        (?:.*\n)* \*
         \* SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-(?:Element|Orbit)-Commercial\.
         \* Please see LICENSE files in the repository root for full details\.
         \*/
        """.trimIndent().toRegex()

    private val enterpriseLicense = """
        /\*
        (?:.*\n)* \*
         \* and (?:Element|Orbit) Software GmbH \(the "(?:Element|Orbit) Group"\) only make this file available
         \* under a proprietary license model\.
         \*
         \* Without a proprietary license with us, you cannot use this file\. The terms of
         \* the proprietary license agreement between you and any member of the (?:Element|Orbit) Group
         \* shall always apply to your use of this file\. Unauthorised use, copying, distribution,
         \* or modification of this file, via any medium, is strictly prohibited\.
         \*
         \* For details about the licensing terms, you must either visit our website or contact
         \* a member of our sales team\.
         \*/
        """.trimIndent().toRegex()

    @Test
    fun `assert that FOSS files have the correct license header`() {
        Konsist
            .scopeFromProject()
            .files
            .filter {
                it.moduleName.startsWith("enterprise").not() &&
                    it.moduleName.contains("rustls-tls").not() &&
                    it.nameWithExtension != "locales.kt" &&
                    it.name.startsWith("Template ").not()
            }
            .also {
                assertThat(it).isNotEmpty()
            }
            .assertTrue {
                publicLicense.containsMatchIn(it.text)
            }
    }

    @Test
    fun `assert that Enterprise files have the correct license header`() {
        Konsist
            .scopeFromProject()
            .files
            .filter {
                it.moduleName.startsWith("enterprise")
            }
            .assertTrue {
                enterpriseLicense.containsMatchIn(it.text)
            }
    }

    @Test
    fun `assert that files do not have double license header`() {
        Konsist
            .scopeFromProject()
            .files
            .filter {
                it.moduleName.endsWith("rustls-tls").not() &&
                it.nameWithExtension != "locales.kt" &&
                it.nameWithExtension != "KonsistLicenseTest.kt" &&
                    it.name.startsWith("Template ").not()
            }
            .assertTrue {
                it.text.count("SPDX-License-Identifier") <= 1
            }
    }
}

private fun String.count(subString: String): Int {
    var count = 0
    var index = 0
    while (true) {
        index = indexOf(subString, index)
        if (index == -1) return count
        count++
        index += subString.length
    }
}
