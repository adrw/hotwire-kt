package misk.db.feature.web.actions.frames

import misk.db.feature.web.create.CreateFormChooseFeatureTypeId
import misk.db.feature.web.create.CreateFormExpandFeatureTypeId
import misk.db.feature.web.create.CreateFormId
import misk.db.feature.web.create.CreateOrUpdateHandler
import misk.db.feature.web.create.FieldCreateValueBoolean
import wisp.logging.getLogger
import xyz.adrw.hotwire.tailwinds.Wrapper
import xyz.adrw.hotwire.templates.buildHtml
import javax.inject.Inject

class TurboRenderHandler @Inject constructor(
  private val createOrUpdateHandler: CreateOrUpdateHandler,
) {
  fun handle(
    frame: String?,
    boolean: String?,
    query: String?,
    limit: String?,
    is_update: String?,
    select_input_id: String?,
    select_input_is_expanded: String?,
    feature_type_index: String?,
    create_name: String?,
    create_value: String?,
    type_java_class_name: String?,
  ): String {
    return buildHtml {
      Wrapper {
        when (frame) {
          CreateFormId -> createOrUpdateHandler.submit()(CreateOrUpdateHandler.Props(
            is_update = is_update?.toBooleanStrictOrNull() ?: false,
            select_input_id = select_input_id,
            select_input_is_expanded = select_input_is_expanded?.toBooleanStrictOrNull() ?: false,
            feature_type_index = feature_type_index?.toIntOrNull(),
            create_name = create_name ?: query,
            create_value = create_value,
            type_java_class_name = type_java_class_name,
          ))
          CreateFormChooseFeatureTypeId, CreateFormExpandFeatureTypeId, FieldCreateValueBoolean -> createOrUpdateHandler.get()(CreateOrUpdateHandler.Props(
            is_update = is_update?.toBooleanStrictOrNull() ?: false,
            select_input_id = select_input_id,
            select_input_is_expanded = select_input_is_expanded?.toBooleanStrictOrNull() ?: false,
            feature_type_index = feature_type_index?.toIntOrNull(),
            create_name = create_name ?: query,
            create_value = create_value,
            type_java_class_name = type_java_class_name,
          ))
          else -> logger.error("GET [turboId=$frame] not found")
        }
      }
    }
  }

  companion object {
    private val logger = getLogger<TurboRenderHandler>()
  }
}
