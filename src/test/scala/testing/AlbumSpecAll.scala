package testing

import org.scalatest.{FunSpec, GivenWhenThen, Tag}
import org.scalatest.Matchers._

class AlbumSpecAll extends FunSpec with GivenWhenThen {

  describe("An album") {
    it("can add an Artist to the album at construction time", Tag("construction")) {
      Given("The album Thriller by Michael Jackson")
      val album = new Album("Thriller", 1981, new Artist("Michael", "Jackson"))
      When("the first act of the album is obtained")
      val act = album.acts.head
      Then("the act should be an instance of Artist")
      act.isInstanceOf[Artist] should be(true)
      And("the artist's first name and last name should be Michael Jackson")
      val artist = act.asInstanceOf[Artist]
      artist.firstName == "Miachel"
      artist.lastName == "Jackson"
      info("This is still pending, since there may be more to accomplish in this test")
    }

    ignore("can add a Producer to an album at construction time") {
      // TODO: Add logic to add a producer
    }
  }
}
