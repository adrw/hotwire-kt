# full-spec 

Based on the SpringBoot example showcasing the full Hotwire spec in [delitescere/hotwire-samples](https://github.com/delitescere/hotwire-samples), but with an Armeria backend. Notably, the lack of WebSocket support in Armeria makes it impossible at current time with vanilla Armeria server to mimick the Hotwire example from [delitescere/hotwire-samples](https://github.com/delitescere/hotwire-samples).  

## Getting Started

```bash
$ gradle :armeria:full-spec:run 
```

Then open in your browser:

- [0.0.0.0:8080/app/kotlinx/](0.0.0.0:8080/app/kotlinx/): demo using `kotlinx.html` templates in Kotlin code
- [0.0.0.0:8080/static/html/mustache/](0.0.0.0:8080/static/html/mustache/): demo using mustache template HTML files on disk