/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.toolbox.test.strings

import tech.orbitweb.orbitx.services.toolbox.api.strings.StringProvider

class FakeStringProvider(
    private val defaultResult: String = "A string"
) : StringProvider {
    var lastResIdParam: Int? = null
    override fun getString(resId: Int): String {
        lastResIdParam = resId
        return defaultResult
    }

    override fun getString(resId: Int, vararg formatArgs: Any?): String {
        lastResIdParam = resId
        return defaultResult + formatArgs.joinToString()
    }

    override fun getQuantityString(resId: Int, quantity: Int, vararg formatArgs: Any?): String {
        lastResIdParam = resId
        return defaultResult + " ($quantity) " + formatArgs.joinToString()
    }

    override fun getSimpleQuantityString(
        resIdForOne: Int,
        resIdForOthers: Int,
        quantity: Int,
        vararg formatArgs: Any?,
    ): String {
        lastResIdParam = if (quantity == 1) resIdForOne else resIdForOthers
        return defaultResult + " ($quantity) " + formatArgs.joinToString()
    }
}
