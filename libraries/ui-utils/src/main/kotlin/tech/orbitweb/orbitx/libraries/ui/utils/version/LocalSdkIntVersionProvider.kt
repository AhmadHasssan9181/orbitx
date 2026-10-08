/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.ui.utils.version

import androidx.compose.runtime.staticCompositionLocalOf
import tech.orbitweb.orbitx.services.toolbox.api.sdk.BuildVersionSdkIntProvider
import tech.orbitweb.orbitx.services.toolbox.impl.sdk.DefaultBuildVersionSdkIntProvider

val LocalSdkIntVersionProvider = staticCompositionLocalOf<BuildVersionSdkIntProvider> { DefaultBuildVersionSdkIntProvider() }
