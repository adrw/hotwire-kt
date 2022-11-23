package xyz.adrw.flagpole.api

import misk.inject.KAbstractModule
import misk.web.WebActionModule

class FlagpoleWebActionsModule: KAbstractModule() {
  override fun configure() {
    install(WebActionModule.create<CreateBillboardAction>())
    install(WebActionModule.create<GetBillboardsAction>())
  }
}