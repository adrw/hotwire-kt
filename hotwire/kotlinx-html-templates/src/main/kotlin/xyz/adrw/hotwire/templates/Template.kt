package xyz.adrw.hotwire.templates

import kotlinx.html.HTMLTag
import kotlinx.html.HtmlInlineTag
import kotlinx.html.TagConsumer
import kotlinx.html.visit

// https://stackoverflow.com/questions/46351598/how-to-represent-web-component-tags-in-kotlin-html-frameworks
class Template(consumer: TagConsumer<*>) : HTMLTag(
  tagName = "template",
  consumer = consumer,
  initialAttributes = mapOf(),
  inlineTag = true,
  emptyTag = false
), HtmlInlineTag

fun TagConsumer<*>.template(block: Template.() -> Unit = {}) {
  Template(this).visit(block)
}
