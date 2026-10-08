/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

import java.io.File

/**
 * Are we building with the enterprise sources?
 */
val isEnterpriseBuild = File("enterprise/README.md").exists()
