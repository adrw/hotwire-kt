package xyz.adrw.hotwire.tailwinds

import kotlinx.html.ButtonType
import kotlinx.html.TagConsumer
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.label
import kotlinx.html.li
import kotlinx.html.role
import kotlinx.html.span
import kotlinx.html.style
import kotlinx.html.ul

data class SelectInputProps(
  val frameId: String,
  val label: String,
  val isExpanded: Boolean,
  val onClickControllerId: String,
  val toggleExpandUrl: String,
  val selectIndexUrl: (Int) -> String,
  val options: List<SelectOption>,
  val selectedIndex: Int,
  /**
   * If there are conflicting text sizes and Tailwinds UI is showing up with very small text,
   *  empty string passed in here will disable all text size CSS and usually solve the problem.
   */
  val textSizeOverride: String? = null
)

data class SelectOption(
  val name: String,
  val avatarUrl: String? = null,
)

fun TagConsumer<*>.SelectInput(props: SelectInputProps) {
//  turbo_frame(id = props.frameId) {
    div {
      label("block ${props.textSizeOverride ?: "text-sm"} font-medium text-gray-700") {
        id = "listbox-label"
        +props.label
      }
      div("relative mt-1") {
//        TODO replace this with an a href and just re-render the turbo frame
//        attributes["data-controller"] = props.onClickControllerId
        a(href = props.toggleExpandUrl) {
          button(classes = "relative w-full cursor-default rounded-md border border-gray-300 bg-white py-2 pl-3 pr-10 text-left shadow-sm focus:border-indigo-500 focus:outline-none focus:ring-1 focus:ring-indigo-500 sm:text-sm") {
            type = ButtonType.button
//          attributes["data-action"] = "click->${props.onClickControllerId}#showOptions"
            attributes["aria-haspopup"] = "listbox"
            attributes["aria-expanded"] = props.isExpanded.toString()
            attributes["aria-labelledby"] = "listbox-label"
//          onClick = props.onClickControllerId
            span("flex items-center") {
              val selectedRow = props.options[props.selectedIndex]
              selectedRow.avatarUrl?.let { url ->
                img(classes = "h-6 w-6 flex-shrink-0 rounded-full") {
//                "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
                  src = url
                  alt = ""
                }
              }
              span("ml-3 block truncate") { +selectedRow.name }
            }
            span("pointer-events-none absolute inset-y-0 right-0 ml-3 flex items-center pr-2") {
//          +"""<!-- Heroicon name: mini/chevron-up-down -->"""
//          svg("h-5 w-5 text-gray-400") {
//            xmlns = "http://www.w3.org/2000/svg"
//            viewbox = "0 0 20 20"
//            fill = "currentColor"
//            attributes["aria-hidden"] = "true"
//            path {
//              attributes["fill-rule"] = "evenodd"
//              d =
//                "M10 3a.75.75 0 01.55.24l3.25 3.5a.75.75 0 11-1.1 1.02L10 4.852 7.3 7.76a.75.75 0 01-1.1-1.02l3.25-3.5A.75.75 0 0110 3zm-3.76 9.2a.75.75 0 011.06.04l2.7 2.908 2.7-2.908a.75.75 0 111.1 1.02l-3.25 3.5a.75.75 0 01-1.1 0l-3.25-3.5a.75.75 0 01.04-1.06z"
//              attributes["clip-rule"] = "evenodd"
//            }
//          }
            }
          }
        }
//      +"""<!--
//      Select popover, show/hide based on select state.
//
//      Entering: ""
//        From: ""
//        To: ""
//      Leaving: "transition ease-in duration-100"
//        From: "opacity-100"
//        To: "opacity-0"
//    -->"""
        if (props.isExpanded) {
          ul("absolute z-10 mt-1 max-h-56 w-full overflow-auto rounded-md bg-white py-1 ${props.textSizeOverride ?: "text-base"} shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none ${props.textSizeOverride ?: "sm:text-sm"}") {
            style = "list-style-type: none;"
//          attributes["data-action"] = "change->${props.onClickControllerId}#chooseOption"

            attributes["tabindex"] = "-1"
            role = "listbox"
            attributes["aria-labelledby"] = "listbox-label"
            attributes["aria-activedescendant"] = "listbox-option-${props.selectedIndex}"
//        +"""<!--
//        Select option, manage highlight styles based on mouseenter/mouseleave and keyboard navigation.
//
//        Highlighted: "text-white bg-indigo-600", Not Highlighted: "text-gray-900"
//      -->"""
            props.options.forEachIndexed { index, selectOption ->
              li("text-gray-900 relative cursor-default select-none py-2 pl-3 pr-9") {
//                attributes["data-${props.onClickControllerId}-target"] = "option"
                a(classes = "text-gray-900 relative cursor-default select-none py-2 pl-3 pr-9") {
                  href = props.selectIndexUrl(index)

                  id = "listbox-option-$index"
                  role = "option"
                  div("flex items-center") {
                    selectOption.avatarUrl?.let { url ->
                      img(classes = "h-6 w-6 flex-shrink-0 rounded-full") {
//                  "https://images.unsplash.com/photo-1491528323818-fdd1faba62cc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
                        src = url
                        alt = ""
                      }
                    }
//            +"""<!-- Selected: "font-semibold", Not Selected: "font-normal" -->"""
                    val selectedCss =
                      if (props.selectedIndex == index) "font-semibold" else "font-normal"
                    span("$selectedCss ml-3 block truncate") { +selectOption.name }
                  }
//          +"""<!--
//          Checkmark, only display for selected option.
//
//          Highlighted: "text-white", Not Highlighted: "text-indigo-600"
//        -->"""
//          span("text-indigo-600 absolute inset-y-0 right-0 flex items-center pr-4") {
//            +"""<!-- Heroicon name: mini/check -->"""
//            svg("h-5 w-5") {
//              xmlns = "http://www.w3.org/2000/svg"
//              viewbox = "0 0 20 20"
//              fill = "currentColor"
//              attributes["aria-hidden"] = "true"
//              path {
//                attributes["fill-rule"] = "evenodd"
//                d =
//                  "M16.704 4.153a.75.75 0 01.143 1.052l-8 10.5a.75.75 0 01-1.127.075l-4.5-4.5a.75.75 0 011.06-1.06l3.894 3.893 7.48-9.817a.75.75 0 011.05-.143z"
//                attributes["clip-rule"] = "evenodd"
//              }
//            }
//          }
                }
              }
            }
          }
        }
      }
//    }
  }
}
