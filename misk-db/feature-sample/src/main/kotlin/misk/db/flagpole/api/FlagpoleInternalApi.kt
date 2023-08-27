package misk.db.flagpole.api

import misk.db.protos.flagpole.CreateBillboardRequest
import misk.db.protos.flagpole.CreateBillboardResponse
import misk.db.protos.flagpole.GetBillboardsRequest
import misk.db.protos.flagpole.GetBillboardsResponse
import javax.inject.Inject

class FlagpoleInternalApi @Inject constructor(
  private val createBillboardAction: CreateBillboardAction,
  private val getBillboardsAction: GetBillboardsAction,
) {
  fun CreateBillboard(request: CreateBillboardRequest): CreateBillboardResponse =
    createBillboardAction.CreateBillboard(request)
  fun GetBillboards(request: GetBillboardsRequest): GetBillboardsResponse =
    getBillboardsAction.GetBillboards(request)
}
