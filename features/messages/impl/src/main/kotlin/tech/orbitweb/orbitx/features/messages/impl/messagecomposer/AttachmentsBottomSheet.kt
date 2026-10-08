/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.messagecomposer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.compound.tokens.generated.CompoundIcons
import tech.orbitweb.orbitx.features.messages.impl.R
import tech.orbitweb.orbitx.libraries.androidutils.ui.hideKeyboard
import tech.orbitweb.orbitx.libraries.designsystem.components.list.ListItemContent
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.IconSource
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.ListItem
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.ModalBottomSheet
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AttachmentsBottomSheet(
    state: MessageComposerState,
    onSendLocationClick: () -> Unit,
    onCreatePollClick: () -> Unit,
    enableTextFormatting: Boolean,
    modifier: Modifier = Modifier,
) {
    val localView = LocalView.current
    var isVisible by rememberSaveable { mutableStateOf(state.showAttachmentSourcePicker) }
    var showStickerPicker by rememberSaveable { mutableStateOf(false) }

    BackHandler(enabled = isVisible) {
        if (showStickerPicker) {
            showStickerPicker = false
        } else {
            isVisible = false
        }
    }

    LaunchedEffect(state.showAttachmentSourcePicker) {
        isVisible = if (state.showAttachmentSourcePicker) {
            // We need to use this instead of `LocalFocusManager.clearFocus()` to hide the keyboard when focus is on an Android View
            localView.hideKeyboard()
            true
        } else {
            showStickerPicker = false
            false
        }
    }
    // Send 'DismissAttachmentMenu' event when the bottomsheet was just hidden
    LaunchedEffect(isVisible) {
        if (!isVisible) {
            showStickerPicker = false
            state.eventSink(MessageComposerEvent.DismissAttachmentMenu)
        }
    }

    if (isVisible) {
        ModalBottomSheet(
            modifier = modifier,
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            ),
            onDismissRequest = { isVisible = false },
            scrollable = false,
        ) {
            if (showStickerPicker) {
                StickerPickerSheet(
                    onSelectSticker = { sticker ->
                        isVisible = false
                        state.eventSink(MessageComposerEvent.PickAttachmentSource.SendSticker(sticker))
                    },
                    onBackClick = { showStickerPicker = false }
                )
            } else {
                AttachmentSourcePickerMenu(
                    state = state,
                    enableTextFormatting = enableTextFormatting,
                    onSendLocationClick = onSendLocationClick,
                    onCreatePollClick = onCreatePollClick,
                    onOpenStickersClick = { showStickerPicker = true },
                )
            }
        }
    }
}

@Composable
private fun AttachmentSourcePickerMenu(
    state: MessageComposerState,
    onSendLocationClick: () -> Unit,
    onCreatePollClick: () -> Unit,
    onOpenStickersClick: () -> Unit,
    enableTextFormatting: Boolean,
) {
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        ListItem(
            modifier = Modifier.clickable { state.eventSink(MessageComposerEvent.PickAttachmentSource.PhotoFromCamera) },
            leadingContent = ListItemContent.Icon(IconSource.Vector(CompoundIcons.TakePhoto())),
            headlineContent = { Text(stringResource(R.string.screen_room_attachment_source_camera_photo)) },
        )
        ListItem(
            modifier = Modifier.clickable { state.eventSink(MessageComposerEvent.PickAttachmentSource.VideoFromCamera) },
            leadingContent = ListItemContent.Icon(IconSource.Vector(CompoundIcons.VideoCall())),
            headlineContent = { Text(stringResource(R.string.screen_room_attachment_source_camera_video)) },
        )
        ListItem(
            modifier = Modifier.clickable { state.eventSink(MessageComposerEvent.PickAttachmentSource.FromGallery) },
            leadingContent = ListItemContent.Icon(IconSource.Vector(CompoundIcons.Image())),
            headlineContent = { Text(stringResource(R.string.screen_room_attachment_source_gallery)) },
        )
        ListItem(
            modifier = Modifier.clickable { state.eventSink(MessageComposerEvent.PickAttachmentSource.FromFiles) },
            leadingContent = ListItemContent.Icon(IconSource.Vector(CompoundIcons.Attachment())),
            headlineContent = { Text(stringResource(R.string.screen_room_attachment_source_files)) },
        )
        ListItem(
            modifier = Modifier.clickable(onClick = onOpenStickersClick),
            leadingContent = ListItemContent.Icon(IconSource.Vector(CompoundIcons.Sticker())),
            headlineContent = { Text(stringResource(tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings.common_sticker)) },
        )
        if (state.canShareLocation) {
            ListItem(
                modifier = Modifier.clickable {
                    state.eventSink(MessageComposerEvent.PickAttachmentSource.Location)
                    onSendLocationClick()
                },
                leadingContent = ListItemContent.Icon(IconSource.Vector(CompoundIcons.LocationPin())),
                headlineContent = { Text(stringResource(R.string.screen_room_attachment_source_location)) },
            )
        }
        ListItem(
            modifier = Modifier.clickable {
                state.eventSink(MessageComposerEvent.PickAttachmentSource.Poll)
                onCreatePollClick()
            },
            leadingContent = ListItemContent.Icon(IconSource.Vector(CompoundIcons.Polls())),
            headlineContent = { Text(stringResource(R.string.screen_room_attachment_source_poll)) },
        )
        if (enableTextFormatting) {
            ListItem(
                modifier = Modifier.clickable { state.eventSink(MessageComposerEvent.ToggleTextFormatting(enabled = true)) },
                leadingContent = ListItemContent.Icon(IconSource.Vector(CompoundIcons.TextFormatting())),
                headlineContent = { Text(stringResource(R.string.screen_room_attachment_text_formatting)) },
            )
        }
    }
}

private data class StickerPack(
    val name: String,
    val stickers: List<String>,
)

@Composable
private fun StickerPickerSheet(
    onSelectSticker: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    val packs = remember {
        listOf(
            StickerPack("🚀 Cosmic", listOf("🚀", "🛸", "🪐", "🌌", "🌠", "🛰️", "👾", "🌕", "☄️", "👨‍🚀", "⚡", "🔮", "👽", "🌟", "🌍")),
            StickerPack("🔥 Reactions", listOf("🔥", "💯", "💀", "🗿", "😂", "🤯", "🥳", "😎", "👀", "✨", "🎉", "👏", "🙌", "🤩", "🫡")),
            StickerPack("🐾 Animals", listOf("🐱", "🐶", "🦊", "🐼", "🦁", "🐯", "🦄", "🐸", "🐵", "🐧", "🦉", "🐙", "🦋", "🌸", "🍕")),
            StickerPack("❤️ Love", listOf("❤️", "💖", "💘", "💝", "💞", "✨", "🥰", "😍", "😘", "💐", "💌", "🌹", "🫶", "💋", "💫")),
        )
    }
    var selectedPackIndex by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Sticker Packs",
                style = ElementTheme.typography.fontHeadingSmMedium,
                color = ElementTheme.colors.textPrimary,
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = onBackClick)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            ) {
                Text(
                    text = "Back",
                    style = ElementTheme.typography.fontBodyMdRegular,
                    color = ElementTheme.colors.textActionAccent,
                )
            }
        }

        // Pack selector tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val iconPrimary = ElementTheme.colors.iconPrimary
            val bgSubtle = ElementTheme.colors.bgSubtleSecondary
            val textPrimary = ElementTheme.colors.textPrimary
            val textSecondary = ElementTheme.colors.textSecondary
            for (index in packs.indices) {
                val pack = packs[index]
                val isSelected = index == selectedPackIndex
                val bgColor = if (isSelected) iconPrimary.copy(alpha = 0.2f) else bgSubtle
                val textColor = if (isSelected) textPrimary else textSecondary
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(bgColor)
                        .clickable { selectedPackIndex = index }
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                ) {
                    Text(
                        text = pack.name,
                        style = ElementTheme.typography.fontBodyMdMedium,
                        color = textColor,
                    )
                }
            }
        }

        // Sticker grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val currentPack = packs[selectedPackIndex]
            items(count = currentPack.stickers.size) { index ->
                val sticker = currentPack.stickers[index]
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSelectSticker(sticker) }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = sticker,
                        style = ElementTheme.typography.fontHeadingLgBold,
                    )
                }
            }
        }
    }
}

@PreviewsDayNight
@Composable
internal fun AttachmentSourcePickerMenuPreview() = ElementPreview {
    AttachmentSourcePickerMenu(
        state = aMessageComposerState(
            canShareLocation = true,
        ),
        onSendLocationClick = {},
        onCreatePollClick = {},
        onOpenStickersClick = {},
        enableTextFormatting = true,
    )
}
