package misk.db.flagpole

import misk.MiskCaller
import misk.db.feature.api.MiskDbFeatureApiAccess
import misk.inject.KAbstractModule
import misk.security.authz.AccessAnnotationEntry
import misk.security.authz.AccessControlModule
import misk.security.authz.DevelopmentOnly
import misk.security.authz.FakeCallerAuthenticator
import misk.security.authz.MiskCallerAuthenticator
import misk.web.dashboard.AdminDashboardAccess

/** Configures access to authenticated web actions and the admin dashboard. */
class FlagpoleAccessModule : KAbstractModule() {
  override fun configure() {
    // Setup fake caller authenticator to handle access
    install(AccessControlModule())
    multibind<MiskCallerAuthenticator>().to<FakeCallerAuthenticator>()

    multibind<AccessAnnotationEntry>().toInstance(
      AccessAnnotationEntry<AdminDashboardAccess>(capabilities = listOf("admins"))
    )

    // Setup authentication in the development environment
    bind<MiskCaller>().annotatedWith<DevelopmentOnly>()
      .toInstance(MiskCaller(user = "development", capabilities = setOf("users", "admins")))

    multibind<AccessAnnotationEntry>().toInstance(
      AccessAnnotationEntry<MiskDbFeatureApiAccess>(capabilities = listOf("admins"))
    )
  }
}
