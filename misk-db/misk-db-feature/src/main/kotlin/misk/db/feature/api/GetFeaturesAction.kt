package misk.db.feature.api

import misk.db.protos.feature.FeatureServiceGetFeaturesBlockingServer
import misk.db.protos.feature.GetFeaturesRequest
import misk.db.protos.feature.GetFeaturesResponse
import misk.web.actions.WebAction
import javax.inject.Inject

class GetFeaturesAction @Inject constructor(
  private val handler: GetFeaturesHandler
): FeatureServiceGetFeaturesBlockingServer, WebAction {
  @MiskDbFeatureApiAccess
  override fun GetFeatures(request: GetFeaturesRequest): GetFeaturesResponse =
    handler.handle(request)
}
