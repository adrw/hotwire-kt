package misk.db.feature.web.create

import kotlinx.html.InputType
import kotlinx.html.a
import kotlinx.html.code
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h5
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.p
import kotlinx.html.textArea
import misk.db.feature.DbFeatureFlags.Companion.evaluateSerialized
import misk.db.feature.FeatureType
import misk.db.feature.getFeatureClazz
import misk.db.feature.toFeatureType
import misk.db.feature.web.PathBuilder
import misk.db.feature.web.PathBuilder.Companion.CreateNameParam
import misk.db.feature.web.PathBuilder.Companion.CreateValueParam
import misk.db.feature.web.PathBuilder.Companion.TypeJavaClassNameParam
import misk.db.feature.web.actions.TurboRenderAction
import misk.db.feature.web.details.DetailsPath
import misk.db.feature.web.results.ResultsPath
import misk.db.protos.feature.Feature
import xyz.adrw.hotwire.tailwinds.SelectInput
import xyz.adrw.hotwire.tailwinds.SelectInputProps
import xyz.adrw.hotwire.tailwinds.SelectOption
import xyz.adrw.hotwire.templates.component
import xyz.adrw.hotwire.templates.turbo_frame

const val CreatePath = "create"
const val CreateFormId = "create-form"
const val CreateFormExpandFeatureTypeId = "$CreateFormId-expand-feature-type"
const val CreateFormChooseFeatureTypeId = "$CreateFormId-choose-feature-type"

// Form Fields
const val FieldFeatureType = "create_feature_type"
const val FieldCreateValueBoolean = "create_value_boolean"

// Feature Field Types
val FeatureTypeOptions = FeatureType.values().map { SelectOption(name = it.name) }

data class CreateHtmlProps(
  val formNameLabelError: String? = null,
  val formValueLabelError: String? = null,
  val formValueLabelHint: String? = null,
  val formTypeJavaClassNameLabelError: String? = null,
  val is_update: Boolean = false,
  val select_input_id: String? = null,
  val select_input_is_expanded: Boolean = false,
  val feature_type_index: Int? = null,
  val create_name: String? = null,
  val create_value: String? = null,
  val type_java_class_name: String? = null,
  val submitStatus: String? = null,
  val updatedFeature: Feature? = null,
)

val selectFeatureTypeLabel = """Type: feature type that your flag always produces when evaluated"""
val SelectFeatureTypeHtml = component<CreateHtmlProps> { props ->
  val isExpanded =
    if (props.select_input_id == CreateFormExpandFeatureTypeId) props.select_input_is_expanded else false
  SelectInput(
    SelectInputProps(
      frameId = CreateFormExpandFeatureTypeId,
      label = selectFeatureTypeLabel,
      isExpanded = isExpanded,
      onClickControllerId = "misk-db-feature-rule-form-select",
      toggleExpandUrl = PathBuilder(
        path = TurboRenderAction.PATH,
        frame = CreateFormExpandFeatureTypeId,
        select_input_id = CreateFormExpandFeatureTypeId,
        select_input_is_expanded = !isExpanded,
        feature_type_index = props.feature_type_index,
        create_name = props.create_name,
        create_value = props.create_value,
        type_java_class_name = props.type_java_class_name,
      ).build(public = false),
      selectIndexUrl = { index ->
        PathBuilder(
          path = TurboRenderAction.PATH,
          frame = CreateFormChooseFeatureTypeId,
          select_input_id = CreateFormExpandFeatureTypeId,
          select_input_is_expanded = false,
          feature_type_index = index,
          create_name = props.create_name,
          create_value = props.create_value,
          type_java_class_name = props.type_java_class_name,
        ).build(public = false)
      },
      options = FeatureTypeOptions,
      selectedIndex = props.feature_type_index ?: 0,
      // Ignore all text size CSS since it conflicts with Blueprint in browser
      textSizeOverride = "",
    )
  )
}

val CreatePageHtml = component<CreateHtmlProps> { props ->
  div {
    id = CreatePath

    CreateForm(props)
  }
}

val CreateForm = component<CreateHtmlProps> { props ->
  turbo_frame(CreatePath) {
    div("mt-1 relative rounded-md shadow-sm") {
      a(classes = "block text-right") {
        href = PathBuilder(path = ResultsPath).build()
        attributes["data-turbo"] = "false"
        +"""All Flags"""
      }

      val featureType = props.feature_type_index?.let { FeatureType.values()[it] }
        ?: props.updatedFeature?.config?.getFeatureClazz()?.toFeatureType()
        ?: FeatureType.BOOLEAN

      val createNameValue = props.updatedFeature?.name ?: props.create_name
      form {
        action = PathBuilder(
          path = TurboRenderAction.PATH,
          frame = CreateFormId,
          is_update = props.is_update,
          feature_type_index = featureType.ordinal,
          // Do not add field names in this path or they will override form inputs
          create_name = if (props.is_update) {
            // We want to set the name input so it is not editable and is included in update form submissions
            createNameValue
          } else null
        ).build(public = false)

        // Choose feature type
        if (props.is_update) {
          label("block font-medium text-gray-700") {
            attributes["htmlFor"] = FieldFeatureType
            +selectFeatureTypeLabel
          }
          input(classes = "form-control block w-full px-3 py-1.5 font-normal text-gray-300 bg-gray-700 bg-gray-100 bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none") {
            type = InputType.text
            name = FieldFeatureType
            id = FieldFeatureType
            // When updating a feature (not new) the field type shouldn't appear as editable
            disabled = true
            value = featureType.name
          }
        } else {
          SelectFeatureTypeHtml(props)
        }

        if (props.formNameLabelError.isNullOrBlank()) {
          label("block font-medium text-gray-700") {
            attributes["htmlFor"] = CreateNameParam
            +"""Name: unique string to be used in code for lookup of your feature flag"""
          }
        } else {
          label("block font-medium text-red-700") {
            attributes["htmlFor"] = CreateNameParam
            +"""${props.formNameLabelError}"""
          }
        }
        input(
          classes = if (props.is_update) {
            "form-control block w-full px-3 py-1.5 font-normal text-gray-300 bg-gray-700 bg-gray-100 bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
          } else {
            "focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-7 pr-12 sm:text-md border-gray-300 rounded-md"
          }
        ) {
          type = InputType.text
          name = CreateNameParam
          id = CreateNameParam
          // When updating a feature (not new) the field name shouldn't appear as editable
          disabled = props.is_update
          if (createNameValue.isNullOrBlank()) {
            placeholder = "enable-alpha"
          } else {
            value = createNameValue
          }
        }

        val createValue: String? = props.updatedFeature?.config?.let { config ->
          config.evaluateSerialized(
            config.getFeatureClazz(),
            "default-key",
            wisp.feature.Attributes()
          ).toString()
        } ?: props.create_value
        val createTypeJavaClassName: String? = props.updatedFeature?.config?.getFeatureClazz()?.name
          ?: props.type_java_class_name
        if (props.formValueLabelError.isNullOrBlank()) {
          label("block font-medium text-gray-700") {
            attributes["htmlFor"] = CreateValueParam
            +"""Value: ${featureType.name.lowercase()} value for flag"""
            props.formValueLabelHint?.let {
              +""". $it"""
            }
          }
        } else {
          label("block font-medium text-red-700") {
            attributes["htmlFor"] = CreateValueParam
            +"""${props.formValueLabelError}"""
          }
        }
        if (featureType == FeatureType.JSON) {
          textArea(
            classes =
            "focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-7 pr-12 sm:text-md border-gray-300 rounded-md"
          ) {
            name = CreateValueParam
            id = CreateValueParam
            if (createValue == null) {
              placeholder = """{"alpha":true,"bravo":false}"""
            } else {
              +createValue.toString()
            }
          }
        } else {
          input(
            classes =
            "focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-7 pr-12 sm:text-md border-gray-300 rounded-md"
          ) {
            type = InputType.text
            name = CreateValueParam
            id = CreateValueParam
            if (createValue == null) {
              placeholder = when (featureType) {
                FeatureType.BOOLEAN -> "false"
                FeatureType.DOUBLE -> "24.24"
                FeatureType.INT -> "42"
                FeatureType.STRING -> "Join today!"
                FeatureType.ENUM -> "BLUE"
                FeatureType.JSON -> """{"alpha":true,"bravo":false}"""
              }
            } else {
              value = createValue.toString()
            }
          }
        }

        // TODO show textarea for JSON
        if (featureType in listOf(FeatureType.ENUM, FeatureType.JSON)) {
          if (props.formTypeJavaClassNameLabelError.isNullOrBlank()) {
            label("block font-medium text-gray-700") {
              attributes["htmlFor"] = CreateValueParam
              +"""Type Class: Java class name used in code that string value is interpreted as"""
            }
          } else {
            label("block font-medium text-red-700") {
              attributes["htmlFor"] = CreateValueParam
              +"""${props.formTypeJavaClassNameLabelError}"""
            }
          }
          input(classes = "focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-7 pr-12 sm:text-md border-gray-300 rounded-md") {
            type = InputType.text
            name = TypeJavaClassNameParam
            id = TypeJavaClassNameParam
            if (createTypeJavaClassName.isNullOrBlank()) {
              placeholder = "xyz.abc.path.to.class\$SubClass"
            } else {
              value = createTypeJavaClassName
            }
          }
        }

        input(
          classes =
          "bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded inline-flex items-center"
        ) {
          type = InputType.submit
        }

        props.updatedFeature?.let {
          val verb = if (props.is_update) "updated" else "created"
          h5 {
            +"""Successfully $verb """
            code {
              a(href = PathBuilder(path = DetailsPath, query = it.name).build()) {
                attributes["data-turbo"] = "false"
                +it.name
              }
            }
            +""", go to """
            a(href = PathBuilder(path = ResultsPath).build()) {
              attributes["data-turbo"] = "false"
              +"""all flags"""
            }
            +"""."""
          }
        }
        props.submitStatus?.let {
          p { +it }
        }
      }
    }
  }
}
