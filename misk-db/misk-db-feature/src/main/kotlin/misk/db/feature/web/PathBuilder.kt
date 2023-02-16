package misk.db.feature.web

// TODO add tests!
data class PathBuilder(
  val path: String? = null,
  val frame: String? = null,
  val boolean: Boolean? = null,
  val query: String? = null,
  val limit: Int? = null,
  val is_update: Boolean? = null,
  val select_input_id: String? = null,
  val select_input_is_expanded: Boolean? = null,
  val show_type_select: Boolean? = null,
  val feature_type_index: Int? = null,
  val create_name: String? = null,
  val create_value: String? = null,
  val type_java_class_name: String? = null,
  ) {
  fun build(
    public: Boolean = true,
  ): String = StringBuilder().apply {
    val normalizedPath = path?.removePrefix("/")

    if (public) append(TabPublicUrlBase) else append(TabPrivateUrlBase)
    if (normalizedPath?.isNotBlank() == true) {
      append("/$normalizedPath/")
    } else if (!public && frame != null) {
      append("/$frame/")
    }

    if (!this.contains("?") && this.last() != '&') append("?")
    frame?.let { append("$FrameParam=$frame&") }
    boolean?.let {
      append("$BooleanParam=$it&")
    }
    query?.let {
      append("$SearchParam=$it&")
    }
    limit?.let {
      append("$LimitParam=$it&")
    }
    is_update?.let {
      append("$IsUpdateParam=$it&")
    }
    select_input_id?.let {
      append("$SelectInputIdParam=$it&")
    }
    select_input_is_expanded?.let {
      append("$SelectInputIsExpandedParam=$it&")
    }
    create_name?.let {
      append("$CreateNameParam=$it&")
    }
    create_value?.let {
      append("$CreateValueParam=$it&")
    }
    type_java_class_name?.let {
      append("$TypeJavaClassNameParam=$it&")
    }
    show_type_select?.let {
      append("$ShowTypeSelectParam=$it&")
    }
    feature_type_index?.let {
      append("$FeatureTypeIndexParam=$it&")
    }
  }.toString().removeSuffix("?").removeSuffix("&")

  companion object {
    const val TabPublicUrlBase = "/_admin/feature"
    const val TabPrivateUrlBase = "/ui/frame"

    const val FrameParam = "frame"
    const val BooleanParam = "boolean"
    const val SearchParam = "q"
    const val LimitParam = "limit"
    const val IsUpdateParam = "is_update"
    const val SelectInputIdParam = "select_input_id"
    const val SelectInputIsExpandedParam = "select_input_is_expanded"
    const val CreateNameParam = "create_name"
    const val CreateValueParam = "create_value"
    const val TypeJavaClassNameParam = "type_java_class_name"
    const val ShowTypeSelectParam = "show_type_select"
    const val FeatureTypeIndexParam = "feature_type_index"
  }
}
