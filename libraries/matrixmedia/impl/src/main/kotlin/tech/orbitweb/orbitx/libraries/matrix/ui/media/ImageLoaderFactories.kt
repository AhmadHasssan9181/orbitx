/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.media

import android.content.Context
import android.os.Build
import coil3.ImageLoader
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import okhttp3.OkHttpClient
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext
import tech.orbitweb.orbitx.libraries.matrix.api.media.MatrixMediaLoader

interface ImageLoaderFactory {
    fun newImageLoader(): ImageLoader
    fun newImageLoader(matrixMediaLoader: MatrixMediaLoader): ImageLoader
}

@ContributesBinding(AppScope::class)
class DefaultImageLoaderFactory(
    @ApplicationContext private val context: Context,
    private val okHttpClient: () -> OkHttpClient,
) : ImageLoaderFactory {
    private val okHttpNetworkFetcherFactory = OkHttpNetworkFetcherFactory(
        callFactory = {
            // Use newBuilder, see https://coil-kt.github.io/coil/network/#using-a-custom-okhttpclient
            okHttpClient().newBuilder().build()
        }
    )

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(okHttpNetworkFetcherFactory)
            }
            .build()
    }

    override fun newImageLoader(matrixMediaLoader: MatrixMediaLoader): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(okHttpNetworkFetcherFactory)
                // Add gif support
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    add(AnimatedImageDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
                add(AvatarDataKeyer())
                add(MediaRequestDataKeyer())
                add(AvatarDataFetcherFactory(matrixMediaLoader))
                add(MediaRequestDataFetcherFactory(matrixMediaLoader))
            }
            .build()
    }
}
