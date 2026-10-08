/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.voicerecorder.impl.di

import android.media.AudioFormat
import android.media.MediaRecorder
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes
import tech.orbitweb.orbitx.libraries.di.RoomScope
import tech.orbitweb.orbitx.libraries.voicerecorder.impl.audio.AudioConfig
import tech.orbitweb.orbitx.libraries.voicerecorder.impl.audio.SampleRate
import tech.orbitweb.orbitx.libraries.voicerecorder.impl.file.VoiceFileConfig
import io.element.android.opusencoder.OggOpusEncoder

@BindingContainer
@ContributesTo(RoomScope::class)
object VoiceRecorderModule {
    @Provides
    fun provideAudioConfig(): AudioConfig {
        val sampleRate = SampleRate
        return AudioConfig(
            format = AudioFormat.Builder()
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setSampleRate(sampleRate.HZ)
                .setChannelMask(AudioFormat.CHANNEL_IN_MONO)
                .build(),
            // 24 kbps
            bitRate = 24_000,
            sampleRate = sampleRate,
            source = MediaRecorder.AudioSource.MIC,
        )
    }

    @Provides
    public fun provideVoiceFileConfig(): VoiceFileConfig =
        VoiceFileConfig(
            cacheSubdir = "voice_recordings",
            fileExt = "ogg",
            mimeType = MimeTypes.Ogg,
        )

    @Provides
    fun provideOggOpusEncoder(): OggOpusEncoder = OggOpusEncoder.create()
}
