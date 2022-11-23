package xyz.adrw.flagpole.api

import xyz.adrw.protos.flagpole.CreateBillboardRequest
import xyz.adrw.protos.flagpole.CreateBillboardResponse
import xyz.adrw.protos.flagpole.GetBillboardsRequest
import xyz.adrw.protos.flagpole.GetBillboardsResponse
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
