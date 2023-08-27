package misk.db.flagpole.persistence

import app.cash.sqldelight.ColumnAdapter
import misk.db.feature.timestampInstantAdapter
import misk.db.flagpole.db.Billboards

/** Typed class to be used instead of raw Longs wherever row Id is used */
data class BillboardId(internal val id: Long) {
  object Adapter : ColumnAdapter<BillboardId, Long> {
    override fun decode(databaseValue: Long) = BillboardId(databaseValue)
    override fun encode(value: BillboardId) = value.id
  }
}

val billboardsAdapter = Billboards.Adapter(
  created_atAdapter = timestampInstantAdapter,
  updated_atAdapter = timestampInstantAdapter,
  idAdapter = BillboardId.Adapter,
)
