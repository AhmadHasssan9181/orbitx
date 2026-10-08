/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.media

import coil3.ImageLoader
import tech.orbitweb.orbitx.libraries.matrix.api.media.MatrixMediaLoader
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeImageLoaderFactory(
    private val newImageLoaderLambda: () -> ImageLoader = { lambdaError() },
    private val newMatrixImageLoaderLambda: (MatrixMediaLoader) -> ImageLoader = { lambdaError() },
) : ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return newImageLoaderLambda()
    }

    override fun newImageLoader(matrixMediaLoader: MatrixMediaLoader): ImageLoader {
        return newMatrixImageLoaderLambda(matrixMediaLoader)
    }
}
