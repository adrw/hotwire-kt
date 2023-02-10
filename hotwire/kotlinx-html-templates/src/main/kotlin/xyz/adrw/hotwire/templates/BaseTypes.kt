package xyz.adrw.hotwire.templates

data class Link(
  val label: String,
  val href: String,
  val isSelected: Boolean = false,
  /** This forces page navigation vs within Turbo Frame navigation by adding a target="_top" attribute. */
  val isPageNavigation: Boolean = false,
  val dataTurbo: Boolean = true,
  val openInNewTab: Boolean = false,
)
