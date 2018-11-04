# Scala Programming

Scala runs on JVM. Scala has its compiler scalac.

In Scala, variables are declared using `var` for mutable variables and `val` for value type variables which are immutable.
In Scala, type information is given after the variable name.

```scala
var name: String = "Piyush"
name = "Ishit"

val anotherName: String = "Piyush"
anotherName = "Ishit" // not allowed
```

**Partial Functions** are partial in the sense that they aren't defined for all possible inputs. They include pattern matching.
If function is called with an input that doesn't match any of the case clauses, a MatchError is thrown at runtime.

