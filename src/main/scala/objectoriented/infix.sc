case class With[A, B](a: A, b: B)
val with1: With[String, Int] = With("Foo", 1)
val with2: String With Int = With("Bar", 2)
// type signature can be written as one of two ways
// With[String, Int]
// String With Int
// Trying to initialize a value in a similar way doesn't work
//val with3 = "one" With 2

Seq(with1, with2) foreach { w =>
  w match {
    case s With i => println(s"$s with $i")
    case _ => println(s"Unknown $w")
  }
}