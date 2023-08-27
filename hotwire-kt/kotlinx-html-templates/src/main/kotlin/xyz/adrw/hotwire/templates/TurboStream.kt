package xyz.adrw.hotwire.templates

import kotlinx.html.HTMLTag
import kotlinx.html.HtmlInlineTag
import kotlinx.html.TagConsumer
import kotlinx.html.visit

// https://stackoverflow.com/questions/46351598/how-to-represent-web-component-tags-in-kotlin-html-frameworks
// <turbo-stream action="replace" target="load" data-th-each="load : ${loadStream}">
class TurboStream(action: TurboStreamAction, targetId: String, consumer: TagConsumer<*>) : HTMLTag(
  tagName = "turbo-stream",
  consumer = consumer,
  initialAttributes = mapOf(
    "action" to action.name.lowercase(),
    "target" to targetId
  ),
  inlineTag = true,
  emptyTag = false
), HtmlInlineTag

fun TagConsumer<*>.turbo_stream(action: TurboStreamAction, targetId: String, block: TurboStream.() -> Unit = {}) {
  TurboStream(action, targetId, this).visit(block)
}

/**
 * Different modes for TurboStream to be handled and result in DOM changes when it arrives via
 *  WebSocket or in HTTP response.
 *
 * https://turbo.hotwired.dev/handbook/streams
 */
enum class TurboStreamAction {
  APPEND,
  PREPEND,
  REPLACE,
  UPDATE,
  REMOVE,
  BEFORE,
  AFTER
}

const val MediaTypeTURBO_STREAM = "text/vnd.turbo-stream.html"
