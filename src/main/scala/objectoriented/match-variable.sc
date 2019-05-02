for {
  // x is of type Any
  x <- Seq(1, 2, 2.7, "one", "two", 'four)
} {
  val str = x match {
      // match only if x equal to 1
    case 1 => "int 1"
      // match any other integer value
    case i: Int => "other int: " + i
    case d: Double => "a double: " + x
    case "one" => "string one"
    case s: String => "other string " + s
      // match all other inputs. This works like a default clause
    case unexpected => "unexpected value: " + unexpected
  }
  println(str)
}

