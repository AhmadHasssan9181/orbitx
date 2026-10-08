/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.media

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaSource(
    /**
     * Url of the media.
     */
    private val url: String,
    /**
     * This is used to hold data for encrypted media.
     */
    val json: String? = null,
) : Parcelable {
    /**
     * A URL with invalid parts (like `#fragment`, if it's an MXC url) removed.
     */
    @IgnoredOnParcel
    val safeUrl = if (url.startsWith("mxc")) {
        // We've seen some MXC urls in the wild having some `mxc://foo/bar#auto` fragment suffix, which is invalid
        url.substringBefore("#")
    } else {
        url
    }
}
