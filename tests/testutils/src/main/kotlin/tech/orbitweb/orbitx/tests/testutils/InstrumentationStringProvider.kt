/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.tests.testutils

import androidx.test.platform.app.InstrumentationRegistry
import tech.orbitweb.orbitx.services.toolbox.api.strings.StringProvider

class InstrumentationStringProvider : StringProvider {
    private val resource = InstrumentationRegistry.getInstrumentation().context.resources
    override fun getString(resId: Int): String {
        return resource.getString(resId)
    }

    override fun getString(resId: Int, vararg formatArgs: Any?): String {
        return resource.getString(resId, *formatArgs)
    }

    override fun getQuantityString(resId: Int, quantity: Int, vararg formatArgs: Any?): String {
        return resource.getQuantityString(resId, quantity, *formatArgs)
    }

    override fun getSimpleQuantityString(resIdForOne: Int, resIdForOthers: Int, quantity: Int, vararg formatArgs: Any?): String {
        val resId = if (quantity == 1) resIdForOne else resIdForOthers
        return resource.getString(resId, *formatArgs)
    }
}
