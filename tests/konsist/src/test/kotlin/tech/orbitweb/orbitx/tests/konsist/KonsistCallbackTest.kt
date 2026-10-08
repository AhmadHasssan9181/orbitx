/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.tests.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertFalse
import org.junit.Test

class KonsistCallbackTest {
    @Test
    fun `we should not invoke Callback Input directly, we should use forEach`() {
        Konsist
            .scopeFromProduction()
            .files
            .assertFalse {
                it.text.contains("callback?.")
            }
    }
}
