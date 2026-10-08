/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.recentemojis.test

import io.element.android.emojibasebindings.Emoji
import io.element.android.emojibasebindings.EmojibaseCategory
import io.element.android.emojibasebindings.EmojibaseStore
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentMap
import tech.orbitweb.orbitx.libraries.recentemojis.api.EmojibaseProvider

class FakeEmojibaseProvider(
    val emojis: Map<EmojibaseCategory, ImmutableList<Emoji>> = mapOf(),
) : EmojibaseProvider {
    override val emojibaseStore: EmojibaseStore
        get() = EmojibaseStore(emojis.toPersistentMap())
}
