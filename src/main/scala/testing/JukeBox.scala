package testing

final class JukeBox private (val albums: Option[List[Album]], val currentTrack: Option[Track]) {
  def this(album: Option[List[Album]]) = this(album, None)

  def readyToPlay = albums.isDefined

  def play = new JukeBox(albums, Some(albums.get(0).tracks.get(0)))

}
