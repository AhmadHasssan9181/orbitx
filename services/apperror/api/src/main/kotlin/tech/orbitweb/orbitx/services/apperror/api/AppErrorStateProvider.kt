/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.apperror.api

fun aAppErrorState() = AppErrorState.Error(
    title = "An error occurred",
    body = "Something went wrong, and the details of that would go here.",
    dismiss = {},
)
