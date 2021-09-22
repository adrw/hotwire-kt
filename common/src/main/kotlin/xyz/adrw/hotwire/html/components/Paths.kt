package xyz.adrw.hotwire.html.components

val Path = "/turbo"
val ScreenParam = "screen"
val BooleanParam = "boolean"
val SearchParam = "search"
val LimitParam = "limit"

data class PathBuilder(
  val screenId: String,
  val booleanCurrent: Boolean? = null,
  val searchQuery: String? = null,
) {
  fun build(): String = StringBuilder().apply {
    append(Path)
    append("?")
    append("$ScreenParam=$screenId&")
    booleanCurrent?.let {
      append("$BooleanParam=$it&")
    }
    searchQuery?.let {
      append("$SearchParam=$it&")
    }
  }.toString().removeSuffix("&")
}
