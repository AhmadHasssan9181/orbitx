/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.firebase

object FirebaseConfig {
    /**
     * It is the push gateway for firebase.
     * Note: pusher_http_url should have path '/_matrix/push/v1/notify' -->
     */
    const val PUSHER_HTTP_URL: String = "https://matrix.org/_matrix/push/v1/notify"

    const val INDEX = 0
    const val NAME = "Firebase"
}
