package testing

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class AlbumTest extends FunSpec {
  describe("An Album") {
    it("can add an Artist object to the album") {
      val album = new Album("Thriller", 1981, new Artist("Miachel", "Jackson"))
      album.title should be("Thriller")
    }

    it("can return the artist from the album") {
      val album = new Album("Thriller", 1981, new Artist("Miachel", "Jackson"))
      val act = album.getArtist.head
      act.isInstanceOf[Artist] should be(true)
    }
  }

  describe("An Album from the 1990s") {

  }

}
