/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.tests.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class KonsistMethodNameTest {
    @Test
    fun `Ensure that method name does not start or end with spaces`() {
        Konsist.scopeFromProject()
            .functions()
            .assertTrue {
                it.name.trim() == it.name
            }
    }
}
