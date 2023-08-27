package misk.db.flagpole

import misk.inject.KAbstractModule
import misk.security.authz.AccessControlModule
import misk.web.MiskWebModule
import misk.web.WebActionModule
import misk.web.resources.StaticResourceAction
import misk.web.resources.StaticResourceEntry

class CommonWebServiceModule(private val config: FlagpoleConfig): KAbstractModule() {
  override fun configure() {
    // Favicon.ico and any other shared static assets available at /static/*
    multibind<StaticResourceEntry>()
      .toInstance(
        StaticResourceEntry(
          url_path_prefix = "/static/",
          resourcePath = "classpath:/web/static/"
        )
      )
    install(WebActionModule.createWithPrefix<StaticResourceAction>(url_path_prefix = "/static/"))
    install(MiskWebModule(config.web))
  }
}
