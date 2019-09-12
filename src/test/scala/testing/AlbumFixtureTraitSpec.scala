package testing

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class AlbumFixtureTraitSpec extends FunSpec {

  trait AlbumFixture {
    val letterFromHome = new Album("Letter From Home", 1989, new Band("Pat Methany Group"))
  }

  describe("The Letter From Home Album by Pat Methany") {
    it("should get the year 1989 from the album")
    new AlbumFixture {
      letterFromHome.year should be(1989)
    }
  }
}
