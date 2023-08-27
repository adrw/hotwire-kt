package misk.db.flagpole.api

import misk.inject.KAbstractModule
import misk.web.WebActionModule

class FlagpoleApiModule: KAbstractModule() {
  override fun configure() {
    install(WebActionModule.create<CreateBillboardAction>())
    install(WebActionModule.create<GetBillboardsAction>())
  }
}
