package objectoriented

object GradeBook {

  def main(args:Array[String]):Unit = {

    val students = List(new Student("Jane", "Doe"), new Student("John", "Doe"))
    for(s <- students) printStudent(s)
  }

  def printStudent(s: Student): Unit = {
    println(s"${s.firstName} ${s.lastName}")
  }
}
