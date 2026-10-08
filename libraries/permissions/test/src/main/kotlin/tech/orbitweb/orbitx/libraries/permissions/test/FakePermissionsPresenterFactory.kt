/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.permissions.test

import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsPresenter

class FakePermissionsPresenterFactory(
    private val permissionPresenter: PermissionsPresenter = FakePermissionsPresenter(),
) : PermissionsPresenter.Factory {
    override fun create(permission: String): PermissionsPresenter {
        return permissionPresenter
    }
}
