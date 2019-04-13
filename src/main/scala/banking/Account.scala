package banking

class Account private(val customer: Customer, val id: String) {
  private[this] var _balance: Int = 0

  customer.addAccount(this)
  def balance = _balance

  def deposit(amount: Int): Boolean = {
    if (amount < 0) false else {_balance += amount; true}
  }

  def withdraw(amount: Int): Boolean = {
    if (amount < 0 || amount > _balance) false
    else {
      _balance -= amount
      true
    }
  }
}

object Account {
  private var nextAccountNumber = 0

  def main(args: Array[String]): Unit = {
    val a = new Account(new Customer("Piyush", "Patel", "ida", new Address(Nil)), "id")
  }

  def apply(c: Customer): Account = {
    nextAccountNumber += 13
    new Account(c, nextAccountNumber.toString)
  }

  def apply(lines: Iterator[String]): Account = {
    ??? // read strings and build an account
  }
}
