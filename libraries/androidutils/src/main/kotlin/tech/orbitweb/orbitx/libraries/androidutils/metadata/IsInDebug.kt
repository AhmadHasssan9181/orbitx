/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.androidutils.metadata

import tech.orbitweb.orbitx.libraries.androidutils.BuildConfig

/**
 * true if the app is built in debug mode.
 * For testing purpose, this can be changed with [withReleaseBehavior].
 */
var isInDebug: Boolean = BuildConfig.DEBUG
    private set

/**
 * Run the lambda simulating the app is in release mode.
 *
 * **IMPORTANT**: this should **ONLY** be used for testing purposes.
 */
fun withReleaseBehavior(lambda: () -> Unit) {
    isInDebug = false
    lambda()
    isInDebug = BuildConfig.DEBUG
}
