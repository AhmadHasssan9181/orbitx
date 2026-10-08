/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications

import androidx.core.content.FileProvider

/**
 * We have to declare our own file provider to avoid collision with other modules
 * having their own.
 */
class NotificationsFileProvider : FileProvider()
