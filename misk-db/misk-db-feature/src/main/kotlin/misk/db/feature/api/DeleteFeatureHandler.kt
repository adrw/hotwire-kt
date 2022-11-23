package misk.db.feature.api

import misk.db.FeatureQueries
import misk.db.protos.feature.DeleteFeatureRequest
import misk.db.protos.feature.DeleteFeatureResponse
import javax.inject.Inject

class DeleteFeatureHandler @Inject constructor(
  private val queries: FeatureQueries
) {
  fun handle(request: DeleteFeatureRequest): DeleteFeatureResponse {
    queries.delete(request.name)
    queries.get(request.name).executeAsOneOrNull() ?: return DeleteFeatureResponse(request.name)

    throw IllegalStateException("Failed to delete flag [name=${request.name}]")
  }
}
