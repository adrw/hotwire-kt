package misk.db.feature.api

import misk.db.FeatureQueries
import misk.db.feature.DbFeatureFlags
import misk.db.feature.retryOnOptimisticLockException
import misk.db.protos.feature.DeleteFeatureRequest
import misk.db.protos.feature.DeleteFeatureResponse
import javax.inject.Inject

class DeleteFeatureHandler @Inject constructor(
  private val queries: FeatureQueries,
  private val dbFeatureFlags: DbFeatureFlags
) {
  fun handle(request: DeleteFeatureRequest): DeleteFeatureResponse {
    retryOnOptimisticLockException {
      queries.delete(request.name)
    }
    dbFeatureFlags.memoryCacheInvalidate(request.name)
    queries.get(request.name).executeAsOneOrNull() ?: return DeleteFeatureResponse(request.name)

    throw IllegalStateException("Failed to delete flag [name=${request.name}]")
  }
}
