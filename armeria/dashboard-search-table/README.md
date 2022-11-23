# dashboard-search-table 

A basic dashboard page showcasing interactive UI (avatar menu open/close) and a typical use case of a search form filtering results in the table below.

For a basic CRUD web app, Hotwire + `kotlinx.html` is a huge breakthrough in being able to build a web experience entirely in Kotlin.

Leveraging Hotwire's Turbo Links, Turbo Frames, and `kotlinx.html` (Kotlin DSL for HTML), the entire dashboard lives in Kotlin code and requires 0 lines of code written in HTML, JS, or CSS.

Key abstractions showcased include `template<ComponentProps> { props -> div { } }` methods and custom HTML elements to allow for defining `turbo_frame` within `kotlinx.html`.

## Getting Started

```bash
$ gradle :armeria:dashboard-search-table:run 
```

Then open in your browser:

- [0.0.0.0:8080/page/dashboard](0.0.0.0:8080/page/dashboard): demo using `kotlinx.html` templates in Kotlin code
