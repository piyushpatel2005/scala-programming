package basics

/**
  * This is a simple HelloWorld class
  */
object HelloWorld {

  val name: String = "Piyush Patel"
  val value = 42
  var age = 25
  age = 23
  val t =(1, 1.2, "hey")
  val (a,b,c) = t
  println(a)
  println(t._3)

  val message = name + " is " + age + " years old."
  val message2 = s"$name is $age years old."
  println(message2)

  val str = s"The second element of t is ${t._2}"
  println(str)

  val square = (x:Int) => x * x
  println(square(23))
  val cube: Double => Double = x => x * x
  println(cube(3))

  val twice: Double => Double = _ * 2
  /**
    * This function greets with hello world message.
    * @param args
    */
  def main(args: Array[String]): Unit = {
    println("hello World")

    var i = 0
    while (i < 3) {
      println(i)
      i += 1
    }

    // This avoids var as if assigns the value to val.
    val response = if (age < 15) {
      "no admission"
    } else {
      "come in"
    }
    println(response)
    for (i <- 1 to 10) {
      print(i + " ")
    }
    println()
    for (i <- 0 until 10; if i % 3 == 0 || i % 5 == 0) {
      print(i + " ")
    }
    println()
    for (i <- 0 until 10; if i % 3 == 0 || i % 5 == 0; j <- 'a' to 'c') {
      print(i + " " + j)
    }
    println()
    val stuff = for {i <- 0 until 10
                     if i % 3 == 0 || i % 5 == 0
                     j <- 'a' to 'c'} yield {
      i -> j
    }
    println(stuff)

    val fizzbuzz = for (i <- 1 to 20) yield {
      (i % 3, i % 5) match {
        case(0,0) => "fizzbuzz"
        case (0, _) => "fizz"
        case (_, 0) => "buzz"
        case _ => i.toString
      }
    }

    val str = "213a"
    val num = try{
      str.toInt
    } catch {
      case ex: NumberFormatException => 0
    }
    println(num)






  }
}
