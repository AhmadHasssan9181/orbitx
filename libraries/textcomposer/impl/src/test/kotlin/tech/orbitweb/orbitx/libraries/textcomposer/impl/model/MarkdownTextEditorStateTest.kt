/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.textcomposer.impl.model

import android.net.Uri
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.core.toRoomIdOrAlias
import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkData
import tech.orbitweb.orbitx.libraries.matrix.api.room.IntentionalMention
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ALIAS
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.permalink.FakePermalinkBuilder
import tech.orbitweb.orbitx.libraries.matrix.test.permalink.FakePermalinkParser
import tech.orbitweb.orbitx.libraries.matrix.test.room.aRoomMember
import tech.orbitweb.orbitx.libraries.slashcommands.api.SlashCommandSuggestion
import tech.orbitweb.orbitx.libraries.textcomposer.impl.mentions.aMentionSpanProvider
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.MentionSpan
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.MentionType
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.ResolvedSuggestion
import tech.orbitweb.orbitx.libraries.textcomposer.model.Suggestion
import tech.orbitweb.orbitx.libraries.textcomposer.model.SuggestionType
import tech.orbitweb.orbitx.libraries.textcomposer.model.aMarkdownTextEditorState
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class MarkdownTextEditorStateTest : RobolectricTest() {
    @Test
    fun `insertMention - room alias - getMentions return empty list`() {
        val state = aMarkdownTextEditorState(initialText = "Hello @", initialFocus = true)
        val suggestion = aRoomAliasSuggestion()
        val mentionSpanProvider = aMentionSpanProvider()
        state.insertSuggestion(suggestion, mentionSpanProvider)
        assertThat(state.getMentions()).isEmpty()
        assertThat(state.text.value().toString()).isEqualTo("Hello @")
    }

    @Test
    fun `insertSuggestion - room alias - with member but failed PermalinkBuilder result`() {
        val state = aMarkdownTextEditorState(initialText = "Hello #", initialFocus = true).apply {
            currentSuggestion = Suggestion(start = 6, end = 7, type = SuggestionType.Room, text = "")
        }
        val suggestion = aRoomAliasSuggestion()
        val permalinkParser = FakePermalinkParser(result = { PermalinkData.RoomLink(A_ROOM_ALIAS.toRoomIdOrAlias()) })
        val mentionSpanProvider = aMentionSpanProvider(permalinkParser = permalinkParser)
        state.insertSuggestion(suggestion, mentionSpanProvider)
        assertThat(state.text.value().toString()).isEqualTo("Hello # ")
    }

    @Test
    fun `insertSuggestion - room alias`() {
        val state = aMarkdownTextEditorState(initialText = "Hello #", initialFocus = true).apply {
            currentSuggestion = Suggestion(start = 6, end = 7, type = SuggestionType.Room, text = "")
        }
        val suggestion = aRoomAliasSuggestion()
        val permalinkParser = FakePermalinkParser(result = { PermalinkData.RoomLink(A_ROOM_ALIAS.toRoomIdOrAlias()) })
        val mentionSpanProvider = aMentionSpanProvider(permalinkParser = permalinkParser)
        state.insertSuggestion(suggestion, mentionSpanProvider)
        assertThat(state.text.value().toString()).isEqualTo("Hello # ")
    }

    @Test
    fun `insertSuggestion - command`() {
        val state = aMarkdownTextEditorState(initialText = "/rai", initialFocus = true).apply {
            currentSuggestion = Suggestion(start = 0, end = 3, type = SuggestionType.Command, text = "/rainbow")
        }
        val suggestion = aSlashCommandSuggestion()
        val permalinkParser = FakePermalinkParser(result = { PermalinkData.RoomLink(A_ROOM_ALIAS.toRoomIdOrAlias()) })
        val mentionSpanProvider = aMentionSpanProvider(permalinkParser = permalinkParser)
        state.insertSuggestion(suggestion, mentionSpanProvider)
        assertThat(state.text.value().toString()).isEqualTo("/rainbow ")
    }

    @Test
    fun `insertSuggestion - with no currentMentionSuggestion does nothing`() {
        val state = aMarkdownTextEditorState(initialText = "Hello @", initialFocus = true)
        val member = aRoomMember()
        val mention = ResolvedSuggestion.Member(member)
        val mentionSpanProvider = aMentionSpanProvider()
        state.insertSuggestion(mention, mentionSpanProvider)
        assertThat(state.getMentions()).isEmpty()
        assertThat(state.text.value().toString()).isEqualTo("Hello @")
    }

    @Test
    fun `insertSuggestion - with member`() {
        val state = aMarkdownTextEditorState(initialText = "Hello @", initialFocus = true).apply {
            currentSuggestion = Suggestion(start = 6, end = 7, type = SuggestionType.Mention, text = "")
        }
        val member = aRoomMember()
        val mention = ResolvedSuggestion.Member(member)
        val permalinkParser = FakePermalinkParser(result = { PermalinkData.UserLink(member.userId) })
        val mentionSpanProvider = aMentionSpanProvider(permalinkParser = permalinkParser)

        state.insertSuggestion(mention, mentionSpanProvider)

        val mentions = state.getMentions()
        assertThat(mentions).isNotEmpty()
        assertThat((mentions.firstOrNull() as? IntentionalMention.User)?.userId).isEqualTo(member.userId)
        assertThat(state.text.value().toString()).isEqualTo("Hello @ ")
    }

    @Test
    fun `insertSuggestion - with @room`() {
        val state = aMarkdownTextEditorState(initialText = "Hello @", initialFocus = true).apply {
            currentSuggestion = Suggestion(start = 6, end = 7, type = SuggestionType.Mention, text = "")
        }
        val mention = ResolvedSuggestion.AtRoom
        val permalinkParser = FakePermalinkParser(result = { PermalinkData.FallbackLink(Uri.EMPTY, false) })
        val mentionSpanProvider = aMentionSpanProvider(permalinkParser = permalinkParser)

        state.insertSuggestion(mention, mentionSpanProvider)

        val mentions = state.getMentions()
        assertThat(mentions).isNotEmpty()
        assertThat(mentions.firstOrNull()).isInstanceOf(IntentionalMention.Room::class.java)
        assertThat(state.text.value().toString()).isEqualTo("Hello @ ")
    }

    @Test
    fun `getMessageMarkdown - when there are no MentionSpans returns the same text`() {
        val text = "No mentions here"
        val state = aMarkdownTextEditorState(initialText = text, initialFocus = true)
        val markdown = state.getMessageMarkdown(FakePermalinkBuilder())
        assertThat(markdown).isEqualTo(text)
    }

    @Test
    fun `getMessageMarkdown - when there are MentionSpans returns the same text with links to the mentions`() {
        val text = "No mentions here"
        val permalinkBuilder = FakePermalinkBuilder(
            permalinkForUserLambda = { Result.success("https://matrix.to/#/$it") },
            permalinkForRoomAliasLambda = { Result.success("https://matrix.to/#/$it") },
        )
        val state = aMarkdownTextEditorState(initialText = text, initialFocus = true)
        state.text.update(aMarkdownTextWithMentions(), needsDisplaying = false)
        val markdown = state.getMessageMarkdown(permalinkBuilder = permalinkBuilder)
        assertThat(markdown).isEqualTo(
            "Hello [@alice:matrix.org](https://matrix.to/#/@alice:matrix.org) and everyone in @room" +
                " and a room [#room:domain.org](https://matrix.to/#/#room:domain.org)"
        )
        assertThat(state.text.value().toString()).isEqualTo("Hello @ and everyone in @ and a room #room:domain.org")
    }

    @Test
    fun `getMentions - when there are no MentionSpans returns empty list of mentions`() {
        val state = aMarkdownTextEditorState(initialText = "Hello @", initialFocus = true)
        assertThat(state.getMentions()).isEmpty()
    }

    @Test
    fun `getMentions - when there are MentionSpans returns a list of mentions`() {
        val state = aMarkdownTextEditorState(initialText = "Hello @", initialFocus = true)
        state.text.update(aMarkdownTextWithMentions(), needsDisplaying = false)
        val mentions = state.getMentions()
        assertThat(mentions).isNotEmpty()
        assertThat((mentions.firstOrNull() as? IntentionalMention.User)?.userId?.value).isEqualTo("@alice:matrix.org")
        assertThat(mentions.lastOrNull()).isInstanceOf(IntentionalMention.Room::class.java)
    }

    private fun aMarkdownTextWithMentions(): CharSequence {
        val userMentionSpan = MentionSpan(MentionType.User(UserId("@alice:matrix.org")))
        val atRoomMentionSpan = MentionSpan(MentionType.Everyone)
        val roomMentionSpan = MentionSpan(MentionType.Room(RoomAlias("#room:domain.org").toRoomIdOrAlias()))
        return buildSpannedString {
            append("Hello ")
            inSpans(userMentionSpan) {
                append("@")
            }
            append(" and everyone in ")
            inSpans(atRoomMentionSpan) {
                append("@")
            }
            append(" and a room ")
            inSpans(roomMentionSpan) {
                append("#room:domain.org")
            }
        }
    }

    private fun aRoomAliasSuggestion(): ResolvedSuggestion.Alias {
        return ResolvedSuggestion.Alias(
            roomAlias = A_ROOM_ALIAS,
            roomId = A_ROOM_ID,
            roomName = null,
            roomAvatarUrl = null
        )
    }

    private fun aSlashCommandSuggestion(): ResolvedSuggestion.Command {
        return ResolvedSuggestion.Command(
            command = SlashCommandSuggestion(
                command = "/rainbow",
                parameters = "param",
                description = "Make the text colorful 🌈",
            ),
        )
    }
}
