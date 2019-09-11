package testing

class AlbumMultipleStorageService {
  val mysqlDAO = DAO.createMySqlDAO
  val db2DAO = DAO.createDB2DAO

  def persist(album: Album): Unit = {
    mysqlDAO.persist(album)
    db2DAO.persist(album)

    album.acts.foreach { act => mysqlDAO.persist(act); db2DAO.persist(act); }
  }
}
