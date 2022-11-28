package misk.db.feature.web.create

import misk.db.feature.DbFeatureFlags.Companion.evaluateSerialized
import misk.db.feature.FeatureType
import misk.db.feature.getFeatureClazz
import misk.db.feature.web.InternalApi
import misk.db.protos.feature.CreateOrUpdateFeatureRequest
import misk.db.protos.feature.Feature
import misk.db.protos.feature.FeatureConfig
import misk.db.protos.feature.FeatureRule
import misk.db.protos.feature.GetFeaturesRequest
import wisp.feature.fromSafeJson
import wisp.moshi.defaultKotlinMoshi
import xyz.adrw.hotwire.templates.component
import javax.inject.Inject

class CreateOrUpdateHandler @Inject private constructor(
  private val api: InternalApi,
) {
  fun get() = component<Props> { props ->
    val response = api.GetFeatures(GetFeaturesRequest(props.create_name))
    val feature = response.features.firstOrNull()

    if (!props.create_name.isNullOrBlank() && props.is_update && feature != null) {
      // Show update form
      CreatePageHtml(
        CreateHtmlProps(
          is_update = true,
          feature_type_index = props.feature_type_index,
          create_name = feature.name,
          create_value = feature.config!!.evaluateSerialized(
            feature.config.getFeatureClazz(),
            "default-key",
            wisp.feature.Attributes()
          ).toString(),
          formValueLabelHint = if (props.feature_type_index == FeatureType.ENUM.ordinal) {
            getEnumHint(feature.config.getFeatureClazz())
          } else null,
          type_java_class_name = feature.config.getFeatureClazz().name
        )
      )
    } else {
      // Show new form
      CreatePageHtml(
        CreateHtmlProps(
          select_input_id = props.select_input_id,
          select_input_is_expanded = props.select_input_is_expanded,
          feature_type_index = props.feature_type_index,
          create_name = props.create_name,
          create_value = props.create_value,
          type_java_class_name = props.type_java_class_name,
        )
      )
    }
  }

  fun submit() = component<Props> { props ->
    var formNameLabelError: String? = null
    var formValueLabelError: String? = null
    var formTypeJavaClassNameLabelError: String? = null
    var formValueLabelHint: String? = null
    var submitStatus: String? = null
    val featureType = FeatureType.values()[props.feature_type_index!!]
    var updatedFeature: Feature? = null
    val allFlags = api.GetFeatures(GetFeaturesRequest(props.create_name))

    if (props.create_name.isNullOrBlank()) {
      formNameLabelError = "Error: name must be non-null and non-empty"
    } else if (!props.is_update && props.create_name in allFlags.features.map { it.name }) {
      formNameLabelError = "Error: name must be unique, ${props.create_name} already exists"
    }
    if (formNameLabelError == null && !props.create_name.isNullOrBlank() && !props.create_value.isNullOrBlank()) {
      val featureRule = try {
        when (featureType) {
          FeatureType.BOOLEAN -> FeatureRule(value_boolean = props.create_value.toBooleanStrict())
          FeatureType.DOUBLE -> FeatureRule(value_double = props.create_value.toDouble())
          FeatureType.INT -> FeatureRule(value_int = props.create_value.toInt())
          FeatureType.STRING -> FeatureRule(value_string = props.create_value)
          FeatureType.ENUM -> {
            buildEnumJsonFeatureRule(props, { e -> formTypeJavaClassNameLabelError = e }) { clazz ->
              // set value label error if value isn't valid and throws
              try {
                java.lang.Enum.valueOf(clazz as Class<out Enum<*>>, props.create_value.uppercase())
              } catch (e: IllegalArgumentException) {
                throw if (e.message?.startsWith("No enum constant") == true) {
                  IllegalArgumentException("No enum constant: ${props.create_value}. ${getEnumHint(clazz)}")
                } else e
              }
              formValueLabelHint = getEnumHint(clazz)
              FeatureRule(
                value_enum = props.create_value,
                type_java_class_name = props.type_java_class_name
              )
            }
          }

          FeatureType.JSON -> {
            buildEnumJsonFeatureRule(props, { e -> formTypeJavaClassNameLabelError = e }) { clazz ->
              // set value label error if value isn't valid and throws
              defaultKotlinMoshi.adapter(clazz).fromSafeJson(props.create_value)
              FeatureRule(
                value_json = props.create_value,
                type_java_class_name = props.type_java_class_name
              )
            }
          }
        }
      } catch (e: Exception) {
        formValueLabelError = "Failed to build FeatureRule with [${e.javaClass.name}=${e.message}]"
        null
      }
      featureRule?.let {
        val response = try {
          api.CreateOrUpdateFeature(
            CreateOrUpdateFeatureRequest(
              name = props.create_name,
              config = FeatureConfig(
                // TODO support multiple rules
                rules = listOf(it)
              )
            )
          )
        } catch (e: Exception) {
          submitStatus = "Failed with [${e.javaClass.name}=${e.message}]"
          null
        }
        updatedFeature = response?.updated_feature ?: let {
          submitStatus = """Failed to create ${props.create_name}, try again"""
          null
        }
      }
    }

    CreatePageHtml(
      CreateHtmlProps(
        formNameLabelError = formNameLabelError,
        formValueLabelError = formValueLabelError,
        formTypeJavaClassNameLabelError = formTypeJavaClassNameLabelError,
        formValueLabelHint = formValueLabelHint,
        feature_type_index = props.feature_type_index,
        is_update = props.is_update,
        updatedFeature = updatedFeature,
        submitStatus = submitStatus,
        // Reset the form on successful submission
        create_name = updatedFeature?.let { null } ?: props.create_name,
        create_value = updatedFeature?.let { null } ?: props.create_value,
        type_java_class_name = updatedFeature?.let { null } ?: props.type_java_class_name,
      )
    )
  }

  private fun getEnumHint(clazz: Class<*>) = "Valid options: ${clazz.fields.joinToString { it.name }}"

  private fun buildEnumJsonFeatureRule(
    props: Props,
    formTypeJavaClassNameLabelError: (String) -> Unit,
    builder: (clazz: Class<*>) -> FeatureRule?
  ) =
    if (props.type_java_class_name.isNullOrBlank()) {
      formTypeJavaClassNameLabelError("Error: type class name must be non-null and non-empty")
      null
    } else {
      // set type label error if class doesn't exist
      try {
        Class.forName(props.type_java_class_name)
      } catch (e: ClassNotFoundException) {
        formTypeJavaClassNameLabelError("Error: failed with [${e.javaClass.name}=${e.message}]")
        null
      }?.let { clazz ->
        builder(clazz)
      }
    }

  data class Props(
    val boolean: Boolean = false,
    val is_update: Boolean = false,
    val select_input_id: String? = null,
    val select_input_is_expanded: Boolean = false,
    val feature_type_index: Int? = null,
    val create_name: String? = null,
    val create_value: String? = null,
    val type_java_class_name: String? = null,
  )
}
