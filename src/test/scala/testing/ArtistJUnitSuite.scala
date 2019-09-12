package testing

import org.junit.{After, Before, Test}
import org.junit.Assert._
import org.scalatest.junit.JUnitSuite

class ArtistJUnitSuite extends JUnitSuite {

  var artist: Artist = _

  @Before
  def startUp(): Unit = {
    artist = new Artist("Kenny", "Rogers")
  }

  @Test
  def addOneAlbumAndGetCopy(): Unit = {
    val copyArtist = artist.addAlbum(new Album("Love will turn you around", 1982, artist))
    assertEquals(copyArtist.albums.size, 1)
    assertEquals(copyArtist.albums.head.title, "Love will turn you around")
  }

  @Test
  def addTwoAlbumsAndGetCopy(): Unit = {
    val copyArtist = artist
      .addAlbum(new Album("Love will turn you around", 1982, artist))
      .addAlbum(new Album("We've got tonight", 1983, artist))
    assertEquals(copyArtist.albums.size, 2)
  }

  @After
  def shutDown(): Unit = {
    this.artist = null
  }
}
