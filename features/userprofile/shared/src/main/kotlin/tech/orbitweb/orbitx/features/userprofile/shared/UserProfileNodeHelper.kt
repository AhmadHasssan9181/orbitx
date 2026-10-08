/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.userprofile.shared

import android.content.Context
import tech.orbitweb.orbitx.libraries.androidutils.R
import tech.orbitweb.orbitx.libraries.androidutils.system.startSharePlainTextIntent
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.notification.CallIntent
import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkBuilder
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import timber.log.Timber

class UserProfileNodeHelper(
    private val userId: UserId,
) {
    interface Callback : NodeInputs {
        fun navigateToAvatarPreview(username: String, avatarUrl: String)
        fun navigateToRoom(roomId: RoomId)
        fun startCall(dmRoomId: RoomId, callIntent: CallIntent)
        fun startVerifyUserFlow(userId: UserId)
    }

    fun onShareUser(
        context: Context,
        permalinkBuilder: PermalinkBuilder,
    ) {
        val permalinkResult = permalinkBuilder.permalinkForUser(userId)
        permalinkResult.onSuccess { permalink ->
            context.startSharePlainTextIntent(
                activityResultLauncher = null,
                chooserTitle = context.getString(CommonStrings.action_share),
                text = permalink,
                noActivityFoundMessage = context.getString(R.string.error_no_compatible_app_found)
            )
        }.onFailure {
            Timber.e(it)
        }
    }
}
