/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.core

import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.core.meta.BuildType

fun aBuildMeta(
    buildType: BuildType = BuildType.DEBUG,
    isDebuggable: Boolean = true,
    applicationName: String = "",
    productionApplicationName: String = applicationName,
    desktopApplicationName: String = applicationName,
    applicationId: String = "",
    isEnterpriseBuild: Boolean = false,
    lowPrivacyLoggingEnabled: Boolean = true,
    versionName: String = "",
    versionCode: Long = 0,
    gitRevision: String = "",
    gitBranchName: String = "",
    flavorDescription: String = "",
    flavorShortDescription: String = "",
) = BuildMeta(
    buildType = buildType,
    isDebuggable = isDebuggable,
    applicationName = applicationName,
    productionApplicationName = productionApplicationName,
    desktopApplicationName = desktopApplicationName,
    applicationId = applicationId,
    isEnterpriseBuild = isEnterpriseBuild,
    lowPrivacyLoggingEnabled = lowPrivacyLoggingEnabled,
    versionName = versionName,
    versionCode = versionCode,
    gitRevision = gitRevision,
    gitBranchName = gitBranchName,
    flavorDescription = flavorDescription,
    flavorShortDescription = flavorShortDescription,
)
