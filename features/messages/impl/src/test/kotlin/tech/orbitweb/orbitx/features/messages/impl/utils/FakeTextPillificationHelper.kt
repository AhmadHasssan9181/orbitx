/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.utils

class FakeTextPillificationHelper(
    private val pillifyLambda: (CharSequence, Boolean) -> CharSequence = { text, _ -> text }
) : TextPillificationHelper {
    override fun pillify(text: CharSequence, pillifyPermalinks: Boolean): CharSequence {
        return pillifyLambda(text, pillifyPermalinks)
    }
}
