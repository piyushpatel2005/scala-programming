package banking

import io.StdIn._

object BankMain {

  val bank = new Bank
  var option = 0
  var customer: Option[Customer] = None
  var account: Option[Account] = None

  val menu = {
    """
      |1. Create Customer
      |2. Select Customer
      |3. Withdraw Money
      |4. Deposit Money
      |5. Open an account
      |6. Change PIN
      |
    """.stripMargin
  }
  while (option != 10) {
    println(menu)
    option = readInt()

    option match {
      case 1 => customer = Some(createCustomer(bank))
      case 2 => customer = selectCustomer(bank)
      case 3 =>
      case 4 =>
      case 5 =>
      case 6 =>
      case 7 =>
      case 8 =>
      case 9 =>
      case 10 =>
      case _ => println("That is not a valid option. Please, select again!")
    }
  }

  private def createCustomer(bank: Bank): Customer = {
    println("What is the customer's first name? ")
    var firstName = readLine()
    println("What is the customer's last name? ")
    var lastName = readLine()
    println("What is the customer's address? ")
    var address = readAddress()
    bank.addCustomer(firstName, lastName, address)
  }

  private def selectCustomer(bank: Bank): Option[Customer] = {
    println("Do you want to find the customer by name or id (name/id)? ")
    val option = readLine()
    if (option == "name") {
      println("What is the customer's first name? ")
      var firstName = readLine()
      println("What is the customer's last name? ")
      var lastName = readLine()
      bank.findCustomer(firstName, lastName)
    } else {
      println("What is the customer id? ")
      val id = readLine()
      bank.findCustomer(id)
    }
  }
  private def readAddress(): Address = ???
}
