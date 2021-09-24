# hotwire-kt 

A collection of Kotlin examples using the Hotwire JS framework to build interactive web apps with a Kotlin Armeria server backend.

Using Hotwire and `kotlinx.html` together has made building web apps fun again since I can build everything I want in sweet, sweet Kotlin. 

## Limitations

Notably, the lack of WebSocket support in Armeria limits it being a complete backend for Hotwire JS. For example, Turbo Streams which require WebSockets do not currently work. Turbo Links and Turbo Frames work well. WebSocket support is being tracked [here](https://github.com/line/armeria/issues/1076) and hopefully will be added soon.

## Workflow

With the [HTML to kotlinx.html IntelliJ Plugin](https://plugins.jetbrains.com/plugin/12205-html-to-kotlinx-html), I could copy pasta any HTML UI code I found into a Kotlin file, and the corresponding `kotlinx.html` DSL would be generated and *just work*. 

The one caveat is that for certain custom tags or attributes or somecases like ButtonType where `kotlinx.html` uses an enum you'll need to manually fix the DSL but any required fixes were always straight forward in my testing.

The overall workflow of copying HTML from UI frameworks like Tailwind CSS, refactoring into Turbo Frame components, and adding props data classes for component inputs, proved to have the best of the React workflows I was used to without all the bad complex abstractions of React, Redux, Webpack, CSS-in-JS, and other novelties of the modern JS front end stack.  

## Resources

* [Armeria](https://armeria.dev)
* [Hotwire](https://hotwired.dev)
* [kotlinx.html](https://kotlinlang.org/docs/typesafe-html-dsl.html)
* [HTML to kotlinx.html IntelliJ Plugin](https://plugins.jetbrains.com/plugin/12205-html-to-kotlinx-html)
* [delitescere/hotwire-samples](https://github.com/delitescere/hotwire-samples)
* [Mustache.java](https://github.com/spullara/mustache.java)
* [Tailwind CSS](https://tailwindcss.com/) 
