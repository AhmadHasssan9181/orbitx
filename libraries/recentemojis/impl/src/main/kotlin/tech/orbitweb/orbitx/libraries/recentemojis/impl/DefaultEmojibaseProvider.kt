/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.recentemojis.impl

import android.content.Context
import io.element.android.emojibasebindings.EmojibaseDatasource
import io.element.android.emojibasebindings.EmojibaseStore
import tech.orbitweb.orbitx.libraries.recentemojis.api.EmojibaseProvider

class DefaultEmojibaseProvider(val context: Context) : EmojibaseProvider {
    override val emojibaseStore: EmojibaseStore by lazy {
        EmojibaseDatasource().load(context)
    }
}
