package xyz.adrw.hotwire

import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.ServerListener
import com.linecorp.armeria.server.file.FileService
import xyz.adrw.hotwire.api.html.PageServiceHtml
import xyz.adrw.hotwire.api.html.TurboServiceHtml

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
      .annotatedService("/page", PageServiceHtml())
      .annotatedService("/turbo", TurboServiceHtml())

      /**
       * Static file service for Hotwire example
       */
      .serviceUnder("/static", FileService.of(
        ClassLoader.getSystemClassLoader(), "/static"
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
