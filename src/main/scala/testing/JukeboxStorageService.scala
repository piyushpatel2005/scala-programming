package testing

class JukeboxStorageService(dao: DAO) {

  def persist(jukeBox: JukeBox): Unit = {
    jukeBox.albums.getOrElse(Nil).foreach {
      album =>
        dao.persist(album)
        album.acts.foreach(act => dao.persist(act))
    }
  }
}
