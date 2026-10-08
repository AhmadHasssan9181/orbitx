/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.dateformatter.impl.previews

import android.content.res.Resources
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import tech.orbitweb.orbitx.services.toolbox.api.strings.StringProvider

class PreviewStringProvider(
    private val resources: Resources
) : StringProvider {
    override fun getString(@StringRes resId: Int): String {
        return resources.getString(resId)
    }

    override fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
        return resources.getString(resId, *formatArgs)
    }

    override fun getQuantityString(@PluralsRes resId: Int, quantity: Int, vararg formatArgs: Any?): String {
        return resources.getQuantityString(resId, quantity, *formatArgs)
    }

    override fun getSimpleQuantityString(resIdForOne: Int, resIdForOthers: Int, quantity: Int, vararg formatArgs: Any?): String {
        val resId = if (quantity == 1) resIdForOne else resIdForOthers
        return resources.getString(resId, *formatArgs)
    }
}
