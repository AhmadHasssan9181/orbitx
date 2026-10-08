/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.model.event

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UnableToDecryptContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UtdCause

open class TimelineItemEncryptedContentProvider : PreviewParameterProvider<TimelineItemEncryptedContent> {
    override val values: Sequence<TimelineItemEncryptedContent>
        get() = sequenceOf(
            aTimelineItemEncryptedContent(),
            aTimelineItemEncryptedContent(
                data = UnableToDecryptContent.Data.MegolmV1AesSha2(
                    sessionId = "sessionId",
                    utdCause = UtdCause.SentBeforeWeJoined,
                )
            ),
            aTimelineItemEncryptedContent(
                data = UnableToDecryptContent.Data.MegolmV1AesSha2(
                    sessionId = "sessionId",
                    utdCause = UtdCause.VerificationViolation,
                )
            ),
            aTimelineItemEncryptedContent(
                data = UnableToDecryptContent.Data.MegolmV1AesSha2(
                    sessionId = "sessionId",
                    utdCause = UtdCause.UnsignedDevice,
                )
            ),
            aTimelineItemEncryptedContent(
                data = UnableToDecryptContent.Data.MegolmV1AesSha2(
                    sessionId = "sessionId",
                    utdCause = UtdCause.HistoricalMessageAndBackupIsDisabled,
                )
            ),
            aTimelineItemEncryptedContent(
                data = UnableToDecryptContent.Data.MegolmV1AesSha2(
                    sessionId = "sessionId",
                    utdCause = UtdCause.HistoricalMessageAndDeviceIsUnverified,
                )
            ),
            aTimelineItemEncryptedContent(
                data = UnableToDecryptContent.Data.MegolmV1AesSha2(
                    sessionId = "sessionId",
                    utdCause = UtdCause.WithheldUnverifiedOrInsecureDevice,
                )
            ),
            aTimelineItemEncryptedContent(
                data = UnableToDecryptContent.Data.MegolmV1AesSha2(
                    sessionId = "sessionId",
                    utdCause = UtdCause.WithheldBySender,
                )
            ),
            aTimelineItemEncryptedContent(
                data = UnableToDecryptContent.Data.MegolmV1AesSha2(
                    sessionId = "sessionId",
                    utdCause = UtdCause.Unknown,
                )
            ),
        )
}

private fun aTimelineItemEncryptedContent(
    data: UnableToDecryptContent.Data = UnableToDecryptContent.Data.Unknown
) = TimelineItemEncryptedContent(
    data = data
)
