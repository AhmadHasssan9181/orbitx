/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes

import org.matrix.rustcomponents.sdk.HomeserverLoginDetails
import org.matrix.rustcomponents.sdk.NoHandle

class FakeFfiHomeserverLoginDetails(
    private val url: String = "https://example.org",
    private val supportsPasswordLogin: Boolean = false,
    private val supportsOAuthLogin: Boolean = false,
    private val supportsSsoLogin: Boolean = false,
) : HomeserverLoginDetails(NoHandle) {
    override fun url(): String = url
    override fun supportsOauthLogin(): Boolean = supportsOAuthLogin
    override fun supportsPasswordLogin(): Boolean = supportsPasswordLogin
    override fun supportsSsoLogin(): Boolean = supportsSsoLogin
}
