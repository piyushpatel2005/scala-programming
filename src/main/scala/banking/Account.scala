package banking

class Account(val customer: Customer, val id: String) {
  private var _balance: Int = 0

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
