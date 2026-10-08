/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.event

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.layout.ContentAvoidingLayoutData
import tech.orbitweb.orbitx.libraries.designsystem.modifiers.onKeyboardContextMenuAction

@Composable
fun ViewOnceMediaBubble(
    mediaType: String,
    onContentClick: (() -> Unit)?,
    onLongClick: (() -> Unit)?,
    onContentLayoutChange: (ContentAvoidingLayoutData) -> Unit,
    modifier: Modifier = Modifier,
    isDestroyed: Boolean = false,
    isMine: Boolean = false,
) {
    val borderColor = if (isDestroyed) {
        Color(0xFF374151).copy(alpha = 0.5f)
    } else {
        Color(0xFF8B5CF6).copy(alpha = 0.7f)
    }

    Box(
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isDestroyed) Color(0xFF0F0E17) else Color(0xFF141224))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .onSizeChanged {
                onContentLayoutChange(ContentAvoidingLayoutData(it.width, it.height))
            }
            .then(
                if (onContentClick != null) {
                    Modifier
                        .combinedClickable(
                            onClick = onContentClick,
                            onLongClick = onLongClick,
                        )
                        .onKeyboardContextMenuAction(onLongClick)
                } else {
                    Modifier
                }
            )
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        brush = if (isDestroyed) {
                            Brush.linearGradient(listOf(Color(0xFF374151), Color(0xFF1F2937)))
                        } else {
                            Brush.linearGradient(listOf(Color(0xFF8B5CF6), Color(0xFF6366F1)))
                        },
                        shape = CircleShape
                    )
                    .border(1.5.dp, if (isDestroyed) Color(0xFF4B5563) else Color(0xFFC4B5FD), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isDestroyed) "✓" else "1",
                    color = if (isDestroyed) Color(0xFF9CA3AF) else Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp
                )
            }
            Column {
                Text(
                    text = if (isDestroyed) "$mediaType • Opened" else mediaType,
                    color = if (isDestroyed) Color(0xFF9CA3AF) else Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                val subtitle = when {
                    isDestroyed -> "Self-destructed • No longer available"
                    isMine -> "View-once sent • Cannot be opened"
                    else -> "Tap to view • Self-destructs after opening"
                }
                Text(
                    text = subtitle,
                    color = if (isDestroyed) Color(0xFF6B7280) else Color(0xFFA78BFA),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
