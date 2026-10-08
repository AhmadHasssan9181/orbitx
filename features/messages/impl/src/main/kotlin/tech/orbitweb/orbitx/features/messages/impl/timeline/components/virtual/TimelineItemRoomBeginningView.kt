/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.virtual

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.features.messages.impl.R
import tech.orbitweb.orbitx.libraries.designsystem.atomic.molecules.ComposerAlertMolecule
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.preview.ROOM_NAME
import tech.orbitweb.orbitx.libraries.designsystem.text.toAnnotatedString
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text
import tech.orbitweb.orbitx.libraries.designsystem.utils.allBooleans
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.room.tombstone.PredecessorRoom

@Composable
fun TimelineItemRoomBeginningView(
    roomName: String?,
    predecessorRoom: PredecessorRoom?,
    isDm: Boolean,
    onPredecessorRoomClick: (RoomId) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (predecessorRoom != null) {
            ComposerAlertMolecule(
                avatar = null,
                content = stringResource(R.string.screen_room_timeline_upgraded_room_message).toAnnotatedString(),
                onSubmitClick = { onPredecessorRoomClick(predecessorRoom.roomId) },
                submitText = stringResource(R.string.screen_room_timeline_upgraded_room_action)
            )
        }
        // Only display for non-DM room
        if (!isDm) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                val text = if (roomName == null) {
                    stringResource(id = R.string.screen_room_timeline_beginning_of_room_no_name)
                } else {
                    stringResource(id = R.string.screen_room_timeline_beginning_of_room, roomName)
                }
                Text(
                    color = ElementTheme.colors.textSecondary,
                    style = ElementTheme.typography.fontBodyMdRegular,
                    text = text,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@PreviewsDayNight
@Composable
internal fun TimelineItemRoomBeginningViewPreview() = ElementPreview {
    Column(verticalArrangement = spacedBy(16.dp)) {
        allBooleans.forEach { isDm ->
            TimelineItemRoomBeginningView(
                predecessorRoom = null,
                roomName = null,
                isDm = isDm,
                onPredecessorRoomClick = {},
            )
            TimelineItemRoomBeginningView(
                predecessorRoom = null,
                roomName = ROOM_NAME,
                isDm = isDm,
                onPredecessorRoomClick = {},
            )
            TimelineItemRoomBeginningView(
                predecessorRoom = PredecessorRoom(RoomId("!roomId:matrix.org")),
                roomName = ROOM_NAME,
                isDm = isDm,
                onPredecessorRoomClick = {},
            )
        }
    }
}
