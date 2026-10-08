/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.textcomposer.impl.mentions

import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkParser
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.permalink.FakePermalinkParser
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.MentionSpanFormatter
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.MentionSpanProvider
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.MentionSpanTheme
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.MentionType

fun aMentionSpanProvider(
    permalinkParser: PermalinkParser = FakePermalinkParser(),
    mentionSpanFormatter: MentionSpanFormatter = object : MentionSpanFormatter {
        override fun formatDisplayText(mentionType: MentionType): CharSequence {
            return mentionType.toString()
        }
    },
    mentionSpanTheme: MentionSpanTheme = MentionSpanTheme(A_USER_ID),
): MentionSpanProvider {
    return MentionSpanProvider(
        permalinkParser = permalinkParser,
        mentionSpanFormatter = mentionSpanFormatter,
        mentionSpanTheme = mentionSpanTheme,
    )
}
