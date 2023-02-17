package misk.db.feature.web

import misk.db.feature.web.PathBuilder.Companion.UI_FRAME_BASE_URL
import misk.db.feature.web.PathBuilder.Companion.TAB_PATH_BASE_URL
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PathBuilderTest {
  @Test
  internal fun happyPath() {
    assertEquals("$TAB_PATH_BASE_URL?q=enable",
      PathBuilder(query = "enable").build(public = true))
    assertEquals("$UI_FRAME_BASE_URL?q=enable",
      PathBuilder(query = "enable").build(public = false))
  }
}
