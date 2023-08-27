package misk.db.feature.api

import misk.db.protos.feature.CreateOrUpdateFeatureRequest
import misk.db.protos.feature.CreateOrUpdateFeatureResponse
import misk.db.protos.feature.FeatureServiceCreateOrUpdateFeatureBlockingServer
import misk.web.actions.WebAction
import javax.inject.Inject

class CreateOrUpdateFeatureAction @Inject constructor(
  private val handler: CreateOrUpdateFeatureHandler
): FeatureServiceCreateOrUpdateFeatureBlockingServer, WebAction {
  @MiskDbFeatureApiAccess
  override fun CreateOrUpdateFeature(request: CreateOrUpdateFeatureRequest): CreateOrUpdateFeatureResponse =
    handler.handle(request)
}
