# hotwire-kt 

A collection of Kotlin examples using the Hotwire JS framework to build interactive web apps with a Kotlin [Misk](https://github.com/cashapp/misk) or [Armeria](https://armeria.dev) server backend.

Using Hotwire and `kotlinx.html` together has made building web apps fun again since I can build everything I want in sweet, sweet Kotlin.

## Getting Started

See the links below to each example README.md for instructions on how to run each project locally and try it in your browser.

## Examples

- [flagpole](./misk-db/misk-db-feature-sample/README.md): Misk admin dashboard tab built with Hotwire, feature flags backed by a SqlDelight database
- [dashboard-search-table](./armeria/dashboard-search-table/README.md): Armeria powered dashboard that searches a large JSON file
- [full-spec](./armeria/full-spec/README.md): Armeria implementation of a Hotwire example elsewhere built in Spring that shows use of different Hotwire patterns (TurboFrames...etc)


## Activate Hermit

Before building any example project, you need to activate the [Hermit](https://go.sqprod.co/hermit/)
environment, unless you are using
the [Hermit Shell Hooks](https://cashapp.github.io/hermit/docs/usage/shell/) or Hermit IntelliJ Plugin.

```shell
. ./bin/activate-hermit
```

## Misk vs Armeria Limitations

Misk supports WebSockets and in theory should be able to support all Hotwire patterns. The existing examples in the repo do not yet showcase TurboStreams but there is no reason why it should not work with Misk.

Notably for Armeria, the lack of WebSocket support in Armeria limits it being a complete backend for Hotwire JS. For example, Turbo Streams which require WebSockets do not currently work. Turbo Links and Turbo Frames work well. WebSocket support is being tracked [here](https://github.com/line/armeria/issues/1076) and hopefully will be added soon.

## Workflow

With the [HTML to kotlinx.html IntelliJ Plugin](https://plugins.jetbrains.com/plugin/12205-html-to-kotlinx-html), I could copy pasta any HTML UI code I found into a Kotlin file, and the corresponding `kotlinx.html` DSL would be generated and *just work*. 

The one caveat is that for certain custom tags or attributes or somecases like ButtonType where `kotlinx.html` uses an enum you'll need to manually fix the DSL. Any required fixes were always straight forward in my testing.

The overall workflow of copying HTML from UI frameworks like Tailwind CSS, refactoring into Turbo Frame components, and adding props data classes for component inputs, proved to have the best of the React workflows I was used to without all the bad complex abstractions of React, Redux, Webpack, CSS-in-JS, and other novelties of the modern JS front end stack.  

```kotlin
import xyz.adrw.hotwire.templates.turbo_frame
import xyz.adrw.hotwire.templates.template
import kotlinx.html.*

data class TableProps(
  val data: List<List<String>>,
  val limit: Int? = null,
  val query: String? = null,
)

val TableId = "table_frame"

val Table = template<TableProps> { props ->
  val header = props.data.first()
  val dataRows = props.query?.let { query ->
    props.data.drop(1).filter { row ->
      row.any { it.contains(query) }
    }
  } ?: props.data.drop(1)
  val truncated = dataRows.take(props.limit ?: 5)

  turbo_frame(TableId) {
    div("flex flex-col") {
      div("-my-2 overflow-x-auto sm:-mx-6 lg:-mx-8") {
        div("py-2 align-middle inline-block min-w-full sm:px-6 lg:px-8") {
          div("shadow overflow-hidden border-b border-gray-200 sm:rounded-lg") {
            table("min-w-full divide-y divide-gray-200") {
              thead("bg-gray-50") {
                tr {
                  header.map {
                    th(classes = "px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider") {
                      attributes["scope"] = "col"
                      +it
                    }
                  }
                    ...

```

The request lifecycle of encoding inputs within the request path and using a when statement to choose which component to return with new props was simple and straightforward, a joy to write and use.

```kotlin
// File: armeria/dashboard-search-table/src/main/kotlin/.../Pages.kt

/**
 * Endpoint that handles interactive UI from Hotwire Turbo Frame related clicks
 * Configuration of which UI to return and input data (ie. from forms) is provided by query parameters
 */
class TurboServiceHtml {
  private val logger = getLogger<TurboServiceHtml>()

  @Get
  @Produces("text/html")
  fun get(params: QueryParams): String {
    val screen = params[ScreenParam]
    val currentValue = params[BooleanParam].toBoolean()
    val searchQuery = params[SearchParam]
    val limit = params[LimitParam]?.toIntOrNull()

    return buildHtml {
      Wrapper("") {
        when (screen) {
          NavbarMobileMenuId -> NavbarMobileMenu(NavbarMobileMenuProps(visible = !currentValue))
          NavbarAvatarMenuId -> NavbarAvatarMenu(NavbarAvatarMenuProps(visible = !currentValue))
          TableId -> Table(TableProps(carsData, limit, searchQuery))
          TableWithQueryId -> TableWithQuery(TableWithQueryProps(carsData, limit, searchQuery))
          else -> logger.error("GET [screen=$screen] not found")
        }
      }
    }
  }
}
```

## Resources

* [Misk](https://github.com/cashapp/misk)
* [Armeria](https://armeria.dev)
* [Hotwire](https://hotwired.dev)
* [kotlinx.html](https://kotlinlang.org/docs/typesafe-html-dsl.html)
* [HTML to kotlinx.html IntelliJ Plugin](https://plugins.jetbrains.com/plugin/12205-html-to-kotlinx-html)
* [delitescere/hotwire-samples](https://github.com/delitescere/hotwire-samples)
* [Mustache.java](https://github.com/spullara/mustache.java)
* [Tailwind CSS](https://tailwindcss.com/)
* [kotlinx.html Template Types](https://the-cogitator.com/posts/blog/2020/07/21/functional-templating-with-kotlin.html)
