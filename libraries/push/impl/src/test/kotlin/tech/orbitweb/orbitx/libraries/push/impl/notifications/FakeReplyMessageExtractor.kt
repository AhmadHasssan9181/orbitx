/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications

import android.content.Intent

class FakeReplyMessageExtractor(
    private val result: String? = null,
) : ReplyMessageExtractor {
    override fun getReplyMessage(intent: Intent): String? {
        return result
    }
}
