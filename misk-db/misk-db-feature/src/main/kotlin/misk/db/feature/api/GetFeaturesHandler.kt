package misk.db.feature.api

import misk.db.FeatureQueries
import misk.db.feature.sanitize
import misk.db.feature.toGetFeaturesResponse
import misk.db.protos.feature.GetFeaturesRequest
import misk.db.protos.feature.GetFeaturesResponse
import javax.inject.Inject

class GetFeaturesHandler @Inject constructor(
  private val queries: FeatureQueries
) {
  fun handle(request: GetFeaturesRequest): GetFeaturesResponse {
    val flags = request.search_query?.let { query ->
      // TODO add proper DB based search with SqlDelight 2.x
      queries.getAll().executeAsList().filter { it.name.contains(query.sanitize()) }
    } ?: queries.getAll().executeAsList()
    val total = queries.count().executeAsOne()
    return flags.sortedBy { it.name }.toGetFeaturesResponse(total)
  }
}
