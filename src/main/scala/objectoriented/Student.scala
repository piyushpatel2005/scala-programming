package objectoriented

class Student (val firstName: String,
               val lastName: String,
               private var quizzes: List[Int]=Nil,
               private var assignments: List[Int]=Nil,
               private var tests: List[Int]=Nil) {

  private def validGrade(grade: Int): Boolean = grade >= -20 && grade <= 120
  def addQuiz(grade: Int): Boolean = if (validGrade(grade)) {
    quizzes ::= grade
    true
  } else false

  def addTest(grade: Int): Boolean = if (validGrade(grade)) {
    quizzes ::= grade
    true
  } else false

  def addAssignment(grade: Int): Boolean = if (validGrade(grade)) {
    quizzes ::= grade
    true
  } else false

  def quizAverage: Double =  if (quizzes.isEmpty) 0.0 else quizzes.sum.toDouble / quizzes.length
  def assignmentAverage: Double = if (assignments.isEmpty) 0.0 else assignments.sum.toDouble / assignments.length
  def testAverage: Double = if (tests.isEmpty) 0.0 else tests.sum.toDouble / tests.length
  def average:Double = quizAverage * 0.1 + assignmentAverage * 0.5 + testAverage * 0.4

}
