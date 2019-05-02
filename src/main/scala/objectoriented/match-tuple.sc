val langs = Seq (
  ("Scala", "Martin", "ODersky"),
  ("Clojure", "Rich", "Hickey"),
  ("Lisp", "John", "McCarthy")
)

for(tuple <- langs) {
  tuple match {
      // match a three element tuple with first element Scala
    case ("Scala",_, _) => println("Found scala")
    case (lang, first, last) => println(s"Found other language: $lang ($first, $last)")
  }
}