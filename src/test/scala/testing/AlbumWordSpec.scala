package testing

import org.scalatest.WordSpec
import org.specs2.matcher.ShouldMatchers

class AlbumWordSpec extends WordSpec with ShouldMatchers {

  "An album" should {
    "throw an IllegalArgumentException if there are no acts when created" in {
      intercept[IllegalArgumentException] {
        new Album("The Joy of Listening to Nothing", 1980, List())
      }
      info("The test at this point should still continue")
      info("since we successfully trapped the exception")
    }
  }
}
