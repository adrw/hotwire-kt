package misk.db.feature.web

import misk.db.feature.web.PathBuilder.Companion.TabPrivateUrlBase
import misk.db.feature.web.PathBuilder.Companion.TabPublicUrlBase
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PathBuilderTest {
  @Test
  internal fun happyPath() {
    assertEquals("$TabPublicUrlBase?q=enable",
      PathBuilder(query = "enable").build(public = true))
    assertEquals("$TabPrivateUrlBase?q=enable",
      PathBuilder(query = "enable").build(public = false))
  }
}
