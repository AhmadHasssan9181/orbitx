/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.intent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.net.toUri
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.MainActivity
import tech.orbitweb.orbitx.libraries.deeplink.api.DeepLinkCreator
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.libraries.push.impl.intent.IntentProvider

@ContributesBinding(AppScope::class)
class DefaultIntentProvider(
    @ApplicationContext private val context: Context,
    private val deepLinkCreator: DeepLinkCreator,
) : IntentProvider {
    override fun getViewRoomIntent(
        sessionId: SessionId,
        roomId: RoomId?,
        threadId: ThreadId?,
        eventId: EventId?,
        extras: Bundle?,
    ): Intent {
        return Intent(context, MainActivity::class.java).apply {
            action = Intent.ACTION_VIEW
            data = deepLinkCreator.create(sessionId, roomId, threadId, eventId).toUri()
            extras?.let(::putExtras)
        }
    }
}
