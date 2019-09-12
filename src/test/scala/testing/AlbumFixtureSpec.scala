package testing

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class AlbumFixtureSpec extends FunSpec {
  def fixture = new {
    val letterFromHome = new Album("Letter From Home", 1989, new Band("Pat Methany Group"))
  }

  describe("The Letter From Home album by Pat Methany") {
    it("should get the year 1989 from the album") {
      val album = fixture.letterFromHome
      album.year should be(1989)
    }
  }
}
