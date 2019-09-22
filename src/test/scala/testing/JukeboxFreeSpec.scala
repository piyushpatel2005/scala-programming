package testing

import org.scalatest.{FreeSpec, GivenWhenThen}
import org.scalatest.Matchers._

class JukeboxFreeSpec extends FreeSpec with GivenWhenThen {

  "Given 3 albums" - {
    val badmotorfinger = new Album("Badmotorfinger", 1991, None, new Band("Soundgarden"))
    val thaDoggFather = new Album("The Dogg Father", 1996, None, new Artist("Snoop Doggy", "Dogg"))
    val satchmoAtPasadena = new Album("Satchmo At Pasadena", 1951, None, new Artist("Louis", "Armstrong"))
    "when a juke box is instantiated it should accept some albums" - {
      val jukebox = new JukeBox(Some(List(badmotorfinger, thaDoggFather, satchmoAtPasadena)))
      "then a jukebox's album catalog size should be 3" in {
        jukebox.albums.get should have size (3)
      }
    }
  }

  "Given three albums where one album is by hall and Oates" - {
    "A jukebox should throw a NonApprovedActException" is (pending)
  }

  "If a jukebox has 1 or more albums, the jukebox is ready to play" in {
    Given("1 or more albums")
    val badmotorfinger = new Album("Badmotorfinger", 1991, None, new Band("Soundgarden"))
    val thaDoggFather = new Album("The Dogg Father", 1996, None, new Artist("Snoop Doggy", "Dogg"))
    val satchmoAtPasadena = new Album("Satchmo At Pasadena", 1951, None, new Artist("Louis", "Armstrong"))

    And("a jukebox instantiate with those albums")
    val jukebox = new JukeBox(Some(List(badmotorfinger, thaDoggFather, satchmoAtPasadena)))

    When("jukebox.readyToPlay is called")
    val result = jukebox.readyToPlay

    Then("result should be true")
    result should be(true)
  }

  "Given no albums" - {
    "when we instantiate a jukebox" - {
      val jukebox = new JukeBox(None)
      "then readyToPlaye" - {
        "should return false" in {
          jukebox.readyToPlay should be(false)
        }
      }
    }
  }

  "Given 1000 album" - {
    "when we instantiate a jukebox" - {
      "then ready to play" - {
        "should return true" ignore {

        }
      }
    }
  }
}
