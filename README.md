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

## Using SBT

```shell
sbt
> help # Describe commands
> tasks # shwo the most commonly used available tasks
> tasks -V # show ALL available tasks
> compile # Incrementally compile the code.
> test # Incrementally compile the code and run tests
> clean # Delete all build artifacts
> ~test # Run incr. compiles and tests whether files are saved.
> console # start Scala REPL
> run # Run one of the main routines in the project
> show x # Show the definition of variable "x"
> eclipse # generate Eclipse project files
> run-main Upper Hello world
> exit # Quit REPL
> :quit
```

```shell
scalac -version
scalac -help
scala -version
scala -help
```

The class and variables are defined like:

```scala
class Upper {
  def upper(strings: String*): Seq[String] = {
    strings.map((s: String) => s.toUpperCase())
  }
}
```

To compile a source file use `scalac <filename>.scala`


We can use TAB key to get diffferent options. There are many methods defined in `Predef` library which are imported automatically. Scala allows function chaining.
Scala contains case clases which also generates a **companion object**. Any time an object and class have the same name and they are defined in the same file, they are companions. Any time we write an object followed by an argument list, Scala looks for an `apply` method to call.

```scala
case class Point(x: Double = 0.0, y: Double = 0.0)
val p1 = Point.apply(1.0, 2.0)
val p2 = Point(1.0, 2.0)
```

`Point.apply` is a factory for contructing Points. It is similar to companion object with following signature.

```scala
object Point {
  def apply(x: Double = 0.0, y: Double = 0.0) = new Point(x,y)
}
```

```scala
case class Point(x: Double = 0.0, y: Double = 0.0)

abstract class Shape() {
  def draw(f: String => Unit) : Unit = f(s"draw: ${this.toString}")
}

case class Circle(center: Point, radius: Double) extends Shape

case class Rectangle(lowerleft: Point, height: Double, width: Double) extends Shape

case class Triangle(point1: Point, point2: Point, point3: Point) extends Shape
```

Case classes don't have class body because the `case` keyword defines all the methods we need, such as `toString` method.

Check `basics.shapes.ShapesDrawingActor`. There we define Messages object that defines most of the mssages we'll send between actors. They work like signals to trigger different behaviour. The case class `Response` is used to send arbitrary string mesage to a sender. In Actor, we need to implement `receive()` method which defines how to handle incoming messages.

Akka guarantees that while each message is being processed, the code won't be preemted by another thread, so the handler code is inherently thread-safe. The body of `receive` method is literal syntax for PartialFunction. **Partial Functions** are partial in the sense that they aren't defined for all possible inputs. They include pattern matching. If function is called with an input that doesn't match any of the case clauses, a MatchError is thrown at runtime. The `case` expressions don't need to be wrapped in curly braces because the next case keyword provide unambiguous boundaries. 

## Variables

Scala allows to initialize variables as immutable or mutable.

```scala
val array: Array[String] = new Array(5)
array = new Array(2) // this fails
array(0) = "Hello" // works
// Define mutable primitive variable
var stockPrice: Double = 100.0
stockPrice = 200.0 // works, here the value is changed not the object reference
```

```scala
class Person(val name: String, var age: Int)
val p = new Person("Piyush Patel", 29)
p.name // Piyush Patel
p.age // 29
p.name = "Another Patel" // fails
p.age = 33 // works
```

Note: Prefer immutable values whenever possible