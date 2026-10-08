/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.pusher

data class SetHttpPusherData(
    val pushKey: String,
    val appId: String,
    val url: String,
    val appDisplayName: String,
    val deviceDisplayName: String,
    val profileTag: String?,
    val lang: String,
    val defaultPayload: String,
    val append: Boolean,
)
