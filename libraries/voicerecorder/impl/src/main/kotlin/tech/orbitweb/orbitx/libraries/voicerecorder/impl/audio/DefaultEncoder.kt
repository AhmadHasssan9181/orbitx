/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.voicerecorder.impl.audio

import dev.zacsweers.metro.ContributesBinding
import io.element.android.opusencoder.OggOpusEncoder
import tech.orbitweb.orbitx.libraries.di.RoomScope
import timber.log.Timber
import java.io.File

/**
 * Safe wrapper for OggOpusEncoder.
 */
@ContributesBinding(RoomScope::class)
class DefaultEncoder(
    private val encoderProvider: () -> OggOpusEncoder,
    config: AudioConfig,
) : Encoder {
    private val bitRate = config.bitRate
    private val sampleRate = config.sampleRate.asEncoderModel()

    private var encoder: OggOpusEncoder? = null
    override fun init(
        file: File,
    ) {
        encoder?.release()
        encoder = encoderProvider().apply {
            init(file.absolutePath, sampleRate)
            setBitrate(bitRate)
            // TODO check encoder application: 2048 (voice, default is typically 2049 as audio)
        }
    }

    override fun encode(
        buffer: ShortArray,
        readSize: Int,
    ) {
        encoder?.encode(buffer, readSize)
            ?: Timber.w("Can't encode when encoder not initialized")
    }

    override fun release() {
        encoder?.release()
        encoder = null
    }
}
