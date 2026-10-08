/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.utils

import tech.orbitweb.orbitx.libraries.textcomposer.mentions.MentionSpanFormatter
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.MentionType

class FakeMentionSpanFormatter(
    private val formatLambda: (MentionType) -> CharSequence = { type -> type.toString() },
) : MentionSpanFormatter {
    override fun formatDisplayText(mentionType: MentionType): CharSequence {
        return formatLambda(mentionType)
    }
}
