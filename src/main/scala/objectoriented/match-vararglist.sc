// An enumeration for the comparison operators, where we assign a name for SQL operator
object Op extends Enumeration {
  type Op = Value
  val EQ = Value("=")
  val NE = Value("!=")
  val LTGT = Value("<>")
  val LT = Value("<")
  val GT = Value(">")
  val GE = Value(">=")
}


import Op._
// Represent a SQL "WHERE x op value" clause, where +op+ is a comparison operator: =, !=, <>. <. <=. > or >=
// case class for WHERE x OP y clauses
case class WhereOp[T](columnNames: String, op: Op, value: T)
// case class for WHERE x IN (val1, val2, ..) clauses
case class WhereIn[T](columnName: String, val1: T, vals: T*)

val wheres = Seq(
  WhereIn("state", "IL", "CA", "VA"),
  WhereOp("state", EQ, "IL"),
  WhereOp("name", EQ, "Buck Trends"),
  WhereOp("age", GT, 29)
)

for(where <- wheres) {
  where match {
      // syntax for matching on a variable argument: name @ _*
    case WhereIn(col, val1, vals @ _*) =>
      val valStr = (val1 +: vals).mkString(", ")
      println(s"WHERE $col IN ($valStr)")
    case WhereOp(col, op, value) => println(s"WHERE $col $op $value")
    case _ => println(s"ERROR: Unknown expression: $where")
  }
}

