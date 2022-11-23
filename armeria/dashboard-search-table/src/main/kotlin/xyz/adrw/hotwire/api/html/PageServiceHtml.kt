package xyz.adrw.hotwire.api.html

import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.Param
import com.linecorp.armeria.server.annotation.Produces
import xyz.adrw.hotwire.html.Pages

class PageServiceHtml {
  @Get("/:path")
  @Produces("text/html")
  fun getPage(@Param path: String): String = Pages.routes()[path]
    ?: throw IllegalArgumentException("Invalid [path=$path]")

}
