package misk.db.feature.web.v1.results

import misk.db.feature.web.PathBuilder
import misk.db.feature.web.v1.details.DetailsPath
import misk.db.protos.feature.Feature
import xyz.adrw.hotwire.tailwinds.TableHeader
import xyz.adrw.hotwire.tailwinds.TableRow
import xyz.adrw.hotwire.templates.Link
import java.time.Instant

val flagHeaders = listOf(
  TableHeader("Created"),
  TableHeader("Name"),
  TableHeader("Last Modified"),
)

data class FlagSearchResultsTableRow(
  val createdAt: Instant,
  val flag: Link,
  val updatedAt: Instant,
) : TableRow {
  override fun cells(): List<*> = listOf(createdAt, flag, updatedAt)

  companion object {
    fun Feature.toTableRow() =
      FlagSearchResultsTableRow(
        createdAt = created_at ?: Instant.EPOCH,
        flag = Link(
          label = name,
          href = PathBuilder(
            path = DetailsPath,
            query = name
          ).build(),
          isPageNavigation = true,
        ),
        updatedAt = updated_at ?: Instant.EPOCH,
      )
  }
}
