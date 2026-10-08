/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.di.annotations

import dev.zacsweers.metro.Qualifier

/**
 * Qualifies a [CoroutineScope] object which represents the base coroutine scope to use for the application.
 */
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Qualifier
annotation class AppCoroutineScope
