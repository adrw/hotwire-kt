package misk.db.feature.api

import misk.db.FeatureQueries
import misk.db.feature.getFeatureClazz
import misk.db.feature.toCreateOrUpdateResponse
import misk.db.protos.feature.CreateOrUpdateFeatureRequest
import misk.db.protos.feature.CreateOrUpdateFeatureResponse
import misk.db.protos.feature.FeatureMetadata
import java.time.Clock
import javax.inject.Inject

class CreateOrUpdateFeatureHandler @Inject constructor(
  private val clock: Clock,
  private val queries: FeatureQueries,
) {
  fun handle(request: CreateOrUpdateFeatureRequest): CreateOrUpdateFeatureResponse {
    check(request.name.isNotBlank()) {
      "name must not be empty"
    }
    checkNotNull(request.config) {
      "config must be non-null"
    }
    val existing = queries.get(request.name).executeAsOneOrNull()
    if (existing == null) {
      queries.insert(
        created_at = clock.instant(),
        updated_at = clock.instant(),
        name = request.name,
        metadata = FeatureMetadata(config = request.config),
      )
    } else {
      val oldType = existing.metadata.config!!.getFeatureClazz()
      val newType = request.config.getFeatureClazz()
      check(oldType == newType) {
        "Can not change feature type after it is created [name=${request.name}][oldType=${oldType}][newType=${newType}]"
      }
      queries.update(
        updated_at = clock.instant(),
        name = request.name,
        metadata = FeatureMetadata(config = request.config),
      )
    }
    val readAfterWrite = queries.get(request.name).executeAsOne()
    return readAfterWrite.toCreateOrUpdateResponse()
  }
}
