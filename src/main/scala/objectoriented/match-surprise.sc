def checkY(y: Int) = {
  for {
    x <- Seq(99, 100, 101)
  } {
    val str = x match {
      case y => "found y"
      case i: Int => "int: " + i
    }
    println(str)
  }
}

checkY(100)

// correct the errors above
def checkY2(y:Int) = {
  for {
    x <- Seq(99, 100, 101)
  } {
    val str = x match {
      case `y` => "found y"
      case i: Int => "int: " + i
    }
    println(str)
  }
}

checkY2(100)