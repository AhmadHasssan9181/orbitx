/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.api.notifications

import tech.orbitweb.orbitx.libraries.preferences.api.store.NotificationSound

/**
 * Reads and writes the sound on the message / ringing-call channels. Writes create a new
 * versioned channel because Android forbids mutating sound after creation.
 */
interface NotificationSoundUpdater {
    fun recreateNoisyChannel(sound: NotificationSound, version: Int)

    fun recreateRingingCallChannel(sound: NotificationSound, version: Int)

    /** Current channel sound classified into [NotificationSound]. Null when the channel doesn't exist. */
    suspend fun readNoisyChannelSound(): NotificationSound?

    /** See [readNoisyChannelSound]. */
    suspend fun readRingingCallChannelSound(): NotificationSound?
}
