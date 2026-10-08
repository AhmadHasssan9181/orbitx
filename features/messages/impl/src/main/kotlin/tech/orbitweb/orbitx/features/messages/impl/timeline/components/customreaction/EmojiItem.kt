/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.customreaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.element.android.emojibasebindings.Emoji
import kotlinx.collections.immutable.persistentListOf
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.features.messages.impl.timeline.a11y.a11yReactionAction
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.text.toDp
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text

@Composable
fun EmojiItem(
    item: Emoji,
    isSelected: Boolean,
    onSelectEmoji: (Emoji) -> Unit,
    modifier: Modifier = Modifier,
    emojiSize: TextUnit = 20.sp,
) {
    val backgroundColor = if (isSelected) {
        ElementTheme.colors.bgActionPrimaryRest
    } else {
        Color.Transparent
    }
    val description = a11yReactionAction(
        emoji = item.unicode,
        userAlreadyReacted = isSelected,
    )
    Box(
        modifier = modifier
            .sizeIn(minWidth = 40.dp, minHeight = 40.dp)
            .background(backgroundColor, CircleShape)
            .clickable(
                enabled = true,
                onClick = { onSelectEmoji(item) },
                indication = ripple(bounded = false, radius = emojiSize.toDp() / 2 + 10.dp),
                interactionSource = remember { MutableInteractionSource() }
            )
            .clearAndSetSemantics {
                contentDescription = description
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.unicode,
            style = LocalTextStyle.current.copy(fontSize = emojiSize),
        )
    }
}

@PreviewsDayNight
@Composable
internal fun EmojiItemPreview() = ElementPreview {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        for (isSelected in listOf(true, false)) {
            EmojiItem(
                item = Emoji(
                    hexcode = "",
                    label = "",
                    tags = null,
                    shortcodes = persistentListOf(),
                    unicode = "👍",
                    skins = null
                ),
                isSelected = isSelected,
                onSelectEmoji = {},
            )
        }
    }
}
