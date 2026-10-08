/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.toolbox.test.sdk

import tech.orbitweb.orbitx.services.toolbox.api.sdk.BuildVersionSdkIntProvider

class FakeBuildVersionSdkIntProvider(
    private val sdkInt: Int
) : BuildVersionSdkIntProvider {
    override fun get(): Int = sdkInt
}
