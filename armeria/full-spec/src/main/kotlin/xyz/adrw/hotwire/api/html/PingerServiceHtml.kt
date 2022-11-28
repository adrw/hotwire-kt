package xyz.adrw.hotwire.api.html

import com.github.mustachejava.DefaultMustacheFactory
import com.github.mustachejava.MustacheFactory
import com.linecorp.armeria.server.annotation.Post
import com.linecorp.armeria.server.annotation.Produces
import java.io.StringReader
import java.io.StringWriter
import java.time.Instant
import java.time.ZoneId

class PingerServiceHtml(
  private val mustacheFactory: MustacheFactory = DefaultMustacheFactory()
) {
  @Post
  @Produces("text/html")
  fun ping(): String {
    val pingTemplate = "app/html/mustache/ping.turbo-stream.html"
    val templateString = GreetingServiceHtml.readToString(pingTemplate)
    val template = mustacheFactory.compile(StringReader(templateString), pingTemplate)
    val writer = StringWriter()
    template.execute(writer, mapOf("pingTime" to Instant.now().atZone(ZoneId.systemDefault())))
    return writer.toString()
  }
}
