package xyz.adrw.hotwire.html.infra

import kotlinx.html.TagConsumer
import kotlinx.html.stream.appendHTML

// https://the-cogitator.com/posts/blog/2020/07/21/functional-templating-with-kotlin.html

fun <T> content(builder: TagConsumer<*>.(T) -> Unit) = builder

typealias LayoutBuilder<T> = TagConsumer<*>.(data: T, renderer: TemplateRenderer) -> Unit

fun <T> layout(
  builder: LayoutBuilder<T>
): LayoutBuilder<T> = { data, renderer ->
  builder(data, renderer)
}

typealias TemplateBuilder<T> = TagConsumer<*>.(data: T) -> Unit

fun <T> template(
  builder: TemplateBuilder<T>
): TemplateBuilder<T> = { data ->
  builder(data)
}

typealias TemplateRenderer = TagConsumer<*>.() -> Unit

fun buildHtml(renderer: TemplateRenderer) = StringBuilder().apply {
  appendHTML().renderer()
}.toString()

