//import { Application, Controller } from "https://unpkg.com/@hotwired/stimulus/dist/stimulus.js"
import { Application, Controller } from "../cache/stimulus.js"
window.Stimulus = Application.start()

Stimulus.register("search-form", class extends Controller {
  static targets = [ "form", "query" ]

  search() {
    clearTimeout(this.timeout)
    this.timeout = setTimeout(() => {
      // TODO find way to calculate entire URL and extract so it all is managed in Kotlin by PathBuilder
      window.Turbo.visit("/secure/search?q=" + this.queryTarget.value)
    }, 500)

  }
})
