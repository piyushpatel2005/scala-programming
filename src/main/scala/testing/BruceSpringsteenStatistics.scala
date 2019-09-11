package testing

object BruceSpringsteenStatistics {
  def numberOfAlbums = BruceSpringsteenFactory.discography.size

  def averageYear = BruceSpringsteenFactory.discography.map(_.year).sum / numberOfAlbums
}
