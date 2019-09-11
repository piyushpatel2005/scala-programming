package testing

class DAOStub extends DAO {
  var count = 0

  def persist[T](t:T): Unit = {
    count = count + 1
  }

  def persistCount = count
}

//class UsingStubUnitSpec extends Specification {
//  "Create 2 albums that we will persist to a stub DAO" in {
//    val dao = new DAOStub
//    dao.persist(new Album("The Roaring Silence", 1976, new Band("Manfred Mann's Earth Band")))
//    dao.persist(new Album("Mechanical Animals", 1998, new Artist("Marilyn", "Manson")))
//    dao.persist must be_==(2)
//  }
//}
