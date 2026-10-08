/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.test

import tech.orbitweb.orbitx.libraries.pushproviders.api.Config

fun aSessionPushConfig(
    url: String = "aUrl",
    pushKey: String = "aPushKey",
) = Config(
    url = url,
    pushKey = pushKey,
)
