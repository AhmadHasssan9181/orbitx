/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.impl.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.PendingIntentCompat
import tech.orbitweb.orbitx.features.call.api.CallData
import tech.orbitweb.orbitx.features.call.impl.DefaultElementCallEntryPoint
import tech.orbitweb.orbitx.features.call.impl.ui.ElementCallActivity

internal object IntentProvider {
    fun createIntent(context: Context, callData: CallData): Intent = Intent(context, ElementCallActivity::class.java).apply {
        putExtra(DefaultElementCallEntryPoint.EXTRA_CALL_TYPE, callData)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_USER_ACTION)
    }

    fun getPendingIntent(context: Context, callData: CallData): PendingIntent {
        return PendingIntentCompat.getActivity(
            context,
            DefaultElementCallEntryPoint.REQUEST_CODE,
            createIntent(context, callData),
            PendingIntent.FLAG_CANCEL_CURRENT,
            false
        )!!
    }
}
