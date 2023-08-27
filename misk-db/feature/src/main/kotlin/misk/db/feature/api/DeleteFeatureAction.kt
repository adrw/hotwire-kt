package misk.db.feature.api

import misk.db.protos.feature.DeleteFeatureRequest
import misk.db.protos.feature.DeleteFeatureResponse
import misk.db.protos.feature.FeatureServiceDeleteFlagBlockingServer
import misk.web.actions.WebAction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteFeatureAction @Inject constructor(
  private val handler: DeleteFeatureHandler
) : FeatureServiceDeleteFlagBlockingServer, WebAction {
  @MiskDbFeatureApiAccess
  override fun DeleteFlag(request: DeleteFeatureRequest): DeleteFeatureResponse =
    handler.handle(request)
}
