/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.util

import org.matrix.rustcomponents.sdk.MessageContent
import org.matrix.rustcomponents.sdk.MessageType
import org.matrix.rustcomponents.sdk.RoomMessageEventContentWithoutRelation
import org.matrix.rustcomponents.sdk.TextMessageContent
import org.matrix.rustcomponents.sdk.contentWithoutRelationFromMessage
import org.matrix.rustcomponents.sdk.messageEventContentFromHtml
import org.matrix.rustcomponents.sdk.messageEventContentFromHtmlAsEmote
import org.matrix.rustcomponents.sdk.messageEventContentFromMarkdown
import org.matrix.rustcomponents.sdk.messageEventContentFromMarkdownAsEmote
import tech.orbitweb.orbitx.libraries.matrix.api.room.IntentionalMention
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.MsgType
import tech.orbitweb.orbitx.libraries.matrix.impl.room.map

/**
 * Creates a [RoomMessageEventContentWithoutRelation] from a body, an html body and a list of mentions.
 */
object MessageEventContent {
    fun from(
        body: String,
        htmlBody: String?,
        intentionalMentions: List<IntentionalMention>,
        msgType: MsgType = MsgType.MSG_TYPE_TEXT,
        asPlainText: Boolean = false,
    ): RoomMessageEventContentWithoutRelation {
        return when {
            asPlainText -> contentWithoutRelationFromMessage(
                MessageContent(
                    msgType = MessageType.Text(
                        TextMessageContent(
                            body = body,
                            formatted = null,
                        )
                    ),
                    body = body,
                    isEdited = false,
                    mentions = null,
                )
            )
            htmlBody != null -> if (msgType == MsgType.MSG_TYPE_EMOTE) {
                messageEventContentFromHtmlAsEmote(body, htmlBody)
            } else {
                messageEventContentFromHtml(body, htmlBody)
            }
            else -> if (msgType == MsgType.MSG_TYPE_EMOTE) {
                messageEventContentFromMarkdownAsEmote(body)
            } else {
                messageEventContentFromMarkdown(body)
            }
        }
            .withMentions(intentionalMentions.map())
    }
}
