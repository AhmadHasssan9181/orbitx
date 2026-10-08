/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.tests.testutils.fake

import android.net.Uri
import tech.orbitweb.orbitx.libraries.androidutils.file.TemporaryUriDeleter
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeTemporaryUriDeleter(
    val deleteLambda: (uri: Uri?) -> Unit = { lambdaError() }
) : TemporaryUriDeleter {
    override fun delete(uri: Uri?) {
        deleteLambda(uri)
    }
}
