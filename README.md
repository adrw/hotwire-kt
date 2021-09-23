# hotwire-kt 

A collection of Kotlin examples using the Hotwire JS framework to build interactive web apps with a Kotlin Armeria server backend.

Using Hotwire and `kotlinx.html` together has made building web apps fun again since I can build everything I want in sweet, sweet Kotlin. 

## Limitations

Notably, the lack of WebSocket support in Armeria limits it being a complete backend for Hotwire JS. For example, Turbo Streams which require WebSockets do not currently work. Turbo Links and Turbo Frames work well. WebSocket support is being tracked [here](https://github.com/line/armeria/issues/1076) and hopefully will be added soon.   

## Resources

* [Armeria](https://armeria.dev)
* [Hotwire](https://hotwired.dev)
* [kotlinx.html](https://kotlinlang.org/docs/typesafe-html-dsl.html)
* [HTML to kotlinx.html IntelliJ Plugin](https://plugins.jetbrains.com/plugin/12205-html-to-kotlinx-html)
* [delitescere/hotwire-samples](https://github.com/delitescere/hotwire-samples)
* [Mustache.java](https://github.com/spullara/mustache.java)
* [Tailwind CSS](https://tailwindcss.com/) 
