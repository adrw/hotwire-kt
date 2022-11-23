//import { Application, Controller } from "https://unpkg.com/@hotwired/stimulus/dist/stimulus.js"
import { Application, Controller } from "../cache/stimulus.js"
window.Stimulus = Application.start()

Stimulus.register("misk-db-feature-search-form", class extends Controller {
  static targets = [ "form", "query" ]

  search() {
    clearTimeout(this.timeout)
    this.timeout = setTimeout(() => {
      // Notably, this doesn't seem to be possible to do with Turbo since it usually fails to paint
      //  on reload given the complexity of the Misk-Web navbar wrapper React shim etc.
      // TODO find way to calculate entire URL and extract so it all is managed in Kotlin by PathBuilder
      window.location.assign("/_admin/feature/?q=" + this.queryTarget.value)
    }, 500)

  }
})
