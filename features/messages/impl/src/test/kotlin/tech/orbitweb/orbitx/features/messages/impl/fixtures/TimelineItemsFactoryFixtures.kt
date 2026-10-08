/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.fixtures

import tech.orbitweb.orbitx.features.messages.impl.messagesummary.FakeMessageSummaryFormatter
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.TimelineItemsFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.TimelineItemsFactoryConfig
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event.TimelineItemContentFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event.TimelineItemContentFailedToParseMessageFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event.TimelineItemContentFailedToParseStateFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event.TimelineItemContentMessageFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event.TimelineItemContentPollFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event.TimelineItemContentProfileChangeFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event.TimelineItemContentRedactedFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event.TimelineItemContentRoomMembershipFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event.TimelineItemContentStateFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event.TimelineItemContentStickerFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event.TimelineItemContentUTDFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event.TimelineItemEventFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.virtual.TimelineItemDaySeparatorFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.virtual.TimelineItemVirtualFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.groups.TimelineItemGrouper
import tech.orbitweb.orbitx.features.messages.impl.utils.FakeTextPillificationHelper
import tech.orbitweb.orbitx.features.messages.test.timeline.FakeHtmlConverterProvider
import tech.orbitweb.orbitx.features.poll.test.pollcontent.FakePollContentStateFactory
import tech.orbitweb.orbitx.libraries.androidutils.filesize.FakeFileSizeFormatter
import tech.orbitweb.orbitx.libraries.dateformatter.test.FakeDateFormatter
import tech.orbitweb.orbitx.libraries.eventformatter.api.TimelineEventFormatter
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventContent
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.libraries.matrix.test.permalink.FakePermalinkParser
import tech.orbitweb.orbitx.libraries.mediaviewer.test.util.FileExtensionExtractorWithoutValidation
import tech.orbitweb.orbitx.services.toolbox.test.strings.FakeStringProvider
import tech.orbitweb.orbitx.tests.testutils.testCoroutineDispatchers
import kotlinx.coroutines.test.TestScope

internal fun TestScope.aTimelineItemsFactoryCreator(): TimelineItemsFactory.Creator {
    return object : TimelineItemsFactory.Creator {
        override fun create(config: TimelineItemsFactoryConfig): TimelineItemsFactory {
            return aTimelineItemsFactory(config)
        }
    }
}

internal fun aTimelineItemContentFactory(
    timelineEventFormatter: TimelineEventFormatter = aTimelineEventFormatter(),
    matrixClient: FakeMatrixClient = FakeMatrixClient(),
): TimelineItemContentFactory = TimelineItemContentFactory(
    messageFactory = TimelineItemContentMessageFactory(
        fileSizeFormatter = FakeFileSizeFormatter(),
        fileExtensionExtractor = FileExtensionExtractorWithoutValidation(),
        htmlConverterProvider = FakeHtmlConverterProvider(),
        permalinkParser = FakePermalinkParser(),
        textPillificationHelper = FakeTextPillificationHelper(),
    ),
    redactedMessageFactory = TimelineItemContentRedactedFactory(),
    stickerFactory = TimelineItemContentStickerFactory(
        fileSizeFormatter = FakeFileSizeFormatter(),
        fileExtensionExtractor = FileExtensionExtractorWithoutValidation()
    ),
    pollFactory = TimelineItemContentPollFactory(FakePollContentStateFactory()),
    utdFactory = TimelineItemContentUTDFactory(),
    roomMembershipFactory = TimelineItemContentRoomMembershipFactory(timelineEventFormatter),
    profileChangeFactory = TimelineItemContentProfileChangeFactory(timelineEventFormatter),
    stateFactory = TimelineItemContentStateFactory(timelineEventFormatter),
    failedToParseMessageFactory = TimelineItemContentFailedToParseMessageFactory(),
    failedToParseStateFactory = TimelineItemContentFailedToParseStateFactory(),
    sessionId = matrixClient.sessionId,
    dateFormatter = FakeDateFormatter(),
    stringProvider = FakeStringProvider(),
)

internal fun TestScope.aTimelineItemsFactory(
    config: TimelineItemsFactoryConfig,
): TimelineItemsFactory {
    val matrixClient = FakeMatrixClient()
    return TimelineItemsFactory(
        dispatchers = testCoroutineDispatchers(),
        eventItemFactoryCreator = object : TimelineItemEventFactory.Creator {
            override fun create(config: TimelineItemsFactoryConfig): TimelineItemEventFactory {
                return TimelineItemEventFactory(
                    contentFactory = aTimelineItemContentFactory(matrixClient = matrixClient),
                    matrixClient = matrixClient,
                    dateFormatter = FakeDateFormatter(),
                    permalinkParser = FakePermalinkParser(),
                    config = config,
                    summaryFormatter = FakeMessageSummaryFormatter(),
                )
            }
        },
        virtualItemFactory = TimelineItemVirtualFactory(
            daySeparatorFactory = TimelineItemDaySeparatorFactory(
                FakeDateFormatter()
            ),
        ),
        timelineItemGrouper = TimelineItemGrouper(),
        config = config
    )
}

internal fun aTimelineEventFormatter(): TimelineEventFormatter {
    return object : TimelineEventFormatter {
        override fun format(content: EventContent, isOutgoing: Boolean, sender: UserId, senderDisambiguatedDisplayName: String): CharSequence? {
            return ""
        }
    }
}
