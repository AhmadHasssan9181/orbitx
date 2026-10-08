/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.tests.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.constructors
import com.lemonappdev.konsist.api.ext.list.withAllParentsOf
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test
import tech.orbitweb.orbitx.libraries.architecture.Presenter

class KonsistPresenterTest {
    @Test
    fun `'Presenter' should not depend on other presenters`() {
        Konsist.scopeFromProject()
            .classes()
            .withAllParentsOf(Presenter::class)
            .constructors
            .assertTrue { constructor ->
                val result = constructor.parameters.none { parameter ->
                    parameter.type.name.endsWith("Presenter")
                }
                result
            }
    }
}
