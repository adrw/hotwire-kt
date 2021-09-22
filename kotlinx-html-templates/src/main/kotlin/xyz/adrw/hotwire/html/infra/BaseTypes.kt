package xyz.adrw.hotwire.html.infra

data class Link(
  val label: String,
  val href: String,
  val isSelected: Boolean = false,
)
