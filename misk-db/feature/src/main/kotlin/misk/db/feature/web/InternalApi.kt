package misk.db.feature.web

import misk.db.feature.api.CreateOrUpdateFeatureHandler
import misk.db.feature.api.DeleteFeatureHandler
import misk.db.feature.api.GetFeaturesHandler
import misk.db.protos.feature.CreateOrUpdateFeatureRequest
import misk.db.protos.feature.CreateOrUpdateFeatureResponse
import misk.db.protos.feature.DeleteFeatureRequest
import misk.db.protos.feature.DeleteFeatureResponse
import misk.db.protos.feature.GetFeaturesRequest
import misk.db.protos.feature.GetFeaturesResponse
import javax.inject.Inject

/** Test the API surface area without gRPC orchestration. */
internal class InternalApi @Inject constructor(
  private val createOrUpdateFeatureHandler: CreateOrUpdateFeatureHandler,
  private val deleteFeatureHandler: DeleteFeatureHandler,
  private val getFeaturesHandler: GetFeaturesHandler,
) {
  fun CreateOrUpdateFeature(request: CreateOrUpdateFeatureRequest): CreateOrUpdateFeatureResponse =
    createOrUpdateFeatureHandler.handle(request)
  fun DeleteFeature(request: DeleteFeatureRequest): DeleteFeatureResponse =
    deleteFeatureHandler.handle(request)
  fun GetFeatures(request: GetFeaturesRequest): GetFeaturesResponse =
    getFeaturesHandler.handle(request)
}
