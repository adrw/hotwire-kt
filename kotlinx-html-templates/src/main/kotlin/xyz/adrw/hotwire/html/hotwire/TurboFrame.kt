package xyz.adrw.hotwire.html.hotwire

import kotlinx.html.HTMLTag
import kotlinx.html.HtmlInlineTag
import kotlinx.html.TagConsumer
import kotlinx.html.visit

// https://stackoverflow.com/questions/46351598/how-to-represent-web-component-tags-in-kotlin-html-frameworks
class TurboFrame(id: String, consumer: TagConsumer<*>) : HTMLTag(
  tagName = "turbo-frame",
  consumer = consumer,
  initialAttributes = mapOf(
    "id" to id
  ),
  inlineTag = true,
  emptyTag = false
), HtmlInlineTag

fun TagConsumer<*>.turbo_frame(id: String, block: TurboFrame.() -> Unit = {}) {
  TurboFrame(id, this).visit(block)
}
