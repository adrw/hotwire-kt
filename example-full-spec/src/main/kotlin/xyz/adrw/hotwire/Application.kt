package xyz.adrw.hotwire

import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.ServerListener
import com.linecorp.armeria.server.file.FileService
import com.linecorp.armeria.server.file.HttpFile
import xyz.adrw.hotwire.api.html.GreetingServiceHtml
import xyz.adrw.hotwire.api.html.KotlinxIndexServiceHtml
import xyz.adrw.hotwire.api.html.PingerServiceHtml
import java.nio.file.Paths

/**
 * Holds app-wide objects, e.g. config.
 */
object ApplicationContext {
  const val SERVICE_NAME: String = "hotwire"
  const val PORT = 8080
}

fun main(args: Array<String>) = Application().run(args)

class Application {
  fun run(args: Array<String>) {
    /**
     * Use the default builder to create the server.
     */
    val serverBuilder = Server.builder()
      .http(ApplicationContext.PORT)

      /**
       * Services for Hotwire example
       */
      .annotatedService("/app/kotlinx/", KotlinxIndexServiceHtml())
      .annotatedService("/app/greeting/", GreetingServiceHtml())
      .annotatedService("/app/fragments/html-head",
        HttpFile.of(Paths.get("/app/html/fragments/html-head.html"))
      )
      .annotatedService("/app/pinger", PingerServiceHtml())

      /**
       * Static file service for Hotwire example
       */
      .serviceUnder("/app", FileService.of(
        ClassLoader.getSystemClassLoader(), "/app/html"
      ))

    val server = serverBuilder.build()

    /**
     * Insert any server specific code here, if any.
     *
     * e.g. get server name:
     *     server.defaultHostname()
     *
     * or add a server listener:
     *     val serverListenerBuilder = ServerListener.builder()
     *     serverListenerBuilder.whenStarting { startSomethingHere }
     *     serverListenerBuilder.whenStopping { stopSomethingHere }
     *     val serverListener = serverListenerBuilder.build()
     *     server.addListener(serverListener)
     */
    val serverListenerBuilder = ServerListener.builder()
    val serverListener = serverListenerBuilder.build()
    server.addListener(serverListener)

    // start the server...
    server.start().join()
  }
}
