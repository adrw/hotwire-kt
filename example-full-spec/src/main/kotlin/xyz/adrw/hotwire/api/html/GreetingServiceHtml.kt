package xyz.adrw.hotwire.api.html

import com.github.mustachejava.DefaultMustacheFactory
import com.github.mustachejava.MustacheFactory
import com.linecorp.armeria.common.QueryParams
import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.Produces
import okio.buffer
import okio.source
import java.io.StringReader
import java.io.StringWriter

class GreetingServiceHtml(
  private val mustacheFactory: MustacheFactory = DefaultMustacheFactory()
) {
  @Get
  @Produces("text/html")
  fun greet(params: QueryParams): String {
    val person = params.get("person")
    val greetingTemplatePath = "app/html/mustache/greeting.html"
    val templateString = readToString(greetingTemplatePath)
    val template = mustacheFactory.compile(StringReader(templateString), greetingTemplatePath)
    val writer = StringWriter()
    template.execute(writer, mapOf("person" to person))
    return writer.toString()
  }

  companion object {
    fun readToString(filepath: String): String {
      // File must exist
      val stream = this::class.java.classLoader.getResourceAsStream(filepath)

      val strBuilder = StringBuilder()
      stream.source().use { fileSource ->
        fileSource.buffer().use { bufferedSource ->
          while (true) {
            val line = bufferedSource.readUtf8Line() ?: break
            strBuilder.appendln(line)
          }
        }
      }
      return strBuilder.toString()
    }
  }
}
