import sun.security.provider.Sun

object WeekDay extends Enumeration {
  type WeekDay = Value
  val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
}
// This get enumeration value (Mon, Tue, etc) in scope
import WeekDay._
def isWorkingDay(d: WeekDay) = ! (d == Sat || d == Sun )

// Iterate through WeekDay
WeekDay.values filter isWorkingDay foreach println
