/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.test.timeline

import androidx.compose.runtime.Composable
import tech.orbitweb.orbitx.features.messages.api.timeline.HtmlConverterProvider
import io.element.android.wysiwyg.utils.HtmlConverter
import org.jsoup.nodes.Document

class FakeHtmlConverterProvider(
    private val transform: (String) -> CharSequence = { it },
    private val transformDom: (Document) -> CharSequence = { it.html() },
) : HtmlConverterProvider {
    @Composable
    override fun Update() = Unit

    override fun provide(): HtmlConverter {
        return object : HtmlConverter {
            override fun fromHtmlToSpans(html: String): CharSequence {
                return transform(html)
            }

            override fun fromDocumentToSpans(dom: Document): CharSequence {
                return transformDom(dom)
            }
        }
    }
}
