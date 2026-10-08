/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.securebackup.impl

import tech.orbitweb.orbitx.libraries.core.log.logger.LoggerTag

private val loggerTag = LoggerTag("SecureBackup")
val loggerTagRoot = LoggerTag("Root", loggerTag)
val loggerTagSetup = LoggerTag("Setup", loggerTag)
val loggerTagDisable = LoggerTag("Disable", loggerTag)
