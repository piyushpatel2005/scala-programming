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

## Notes

[Scala Basics](notes/basics.md)

[Collections](notes/oop.md)

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

In Scala, we can easily create Range using Range literal.

```scala
1 to 10 // create range of Int 1 to 10 including both numbers
1 until 10 // 1 to 9
1 to 10 by 3 // 1,4, 7, 10
10 to 1 by -3 // 10,7,4,1
1.1f to 10.3f by 3.1f // NumericRange(1.1, 4.2, 7.2999997)
'a' to 'g' by 3
BigInt(1) to BigInt(10) by 3
```

### Partial Functions

Partial functions are partial means they are not defined for all possible inputs, only those inputs that match at least one of the specified case clauses. If input doesn't match one of the case clauses, a `MatchError` is thrown at runtime. You can test if a PartialFunction will match an input using `isDefinedAt` method. We can also chain PartialFunctions together like `pf1 orElse pf2 orElse pf3`. If pf1 doesn't match, pf2 is tried, then pf3 etc.

```scala
def m1[A](a:A, f:A => String) = f(a)
def m2[A](A:A)(f:A => String) = f(a)
m1(100, i => s"$i + $i") // error: missing parameter type
m2(100)(i => s"$i + $i") // no errors
```

For m1 function, Scala can't infer the type of the i argument for the function. For m2, it can.

## Futures

The `scala.concurrent.Future` API uses implicit arguments to reduce boilerplate in code. Akka uses Futures. When we wrap some work in a Future, the work is executed asynchronously. Future api allwos to provide callbacks that will be invoked when the result is ready.

Check [future example](src/main/scala/basics/futures.sc) for simple example.

Creating recursive functions require recursive calls.

```scala
def factorial(i: Int): Long = {
  def fact(i: Int, accumulator: Int): Long = {
    if(i <= 1) accumulator
    else fact(i - 1, i * accumulator)
  }
  // If we forget to call this function, we get compiler error that Unit is found, but Long is required.
  fact(i, 1)
}

(0 to 5) foreach ( i => println(factorial(i)) )
```

JVM and many other languages don't do tail-call optimization which would convert a tail-recursive function into a loop. The tail-call means that the recursive call is the last thing done in the expression. If you want to make sure that you wrote it correctly and compiler did perform the optimization, you can tell compiler by annotation `tailrec`

The nested inner function can see anything in scope, including arguments passed to the outer function.

We need to specify type of the variable when:
- A mutable var or immutable val declaration where you don't assign a value.
- All method parameters
- Method return types in following cases:
    - When you explicitly call return in a method
    - When a method is recursive
    - When two or more methods are overloaded and one of them calls another, calling method needs a return type annotation.
    - When the inferred return type would be more general than you intended (especially in if...else where one block returns different datatype than another.)
    
```scala
def joiner(strings: String*): String = strings.mkString("-")
```

The "*" after the String in the argument list says "Zero or more Strings".  This can be specified as 

`def joiner (strings: List[String]): String = joiner(strings: _*)`

When developing APIs that are built separately from their clients, declare method return types explicitly and use the most general return type you can. You may see `NoSuchMethodError` if the method return type differs.

There is difference betweeen following two definitions.

```scala
// procedure meant for side-effects only with return type Unit
def double(i:  Int) {2 * i}
// function always returns a value
def double(i: Int) = {2 * i}
```

When the return type of a method is inferred and you don't use an equals sign before the opening curly brace for the method body, Scala infers a Unit return type even when the last expession in the method is a value of another type. That's why procedure syntax is deprecated. `Unit` is actually a type with a single value, named (), which is histsorical convention in functional languages. It behaves similar to void. Scala doesn't have `continue` and `break` keywords.

**Literal** values are primitive values which are easy to create. They are limited by the type of the variable. A string literal is a sequence of characters enclosed in double quotes or triple quotes.

```scala
"""
 Programming \nScala is fun!
"""

def goodbye(name: String) = s"""xxxGoodbye, ${name}yyy
  xxxCome again!yyy""".stripPrefix("xxx").stripSuffix("yyy")
goodbye("Scala")
```

Scala supports symbols. A symbol literal is a single quote (') followed by one or more digits, letters or underscores. A symbol literal `'id` is a shorthand expression `scala.Symbol("id")`. A function literal is as follows:

```scala
val f1: (Int, String) => String = (i, s) => s + i
val f2: Function2[Int, String, String] = (i, s) => s + i
```

If we want to pass multiple values from a function, we can do so using tuple. Tuples are index one.

```scala
val t1: (Int, String) = (1, "one")
val t2: Tuple2[Int, String] = (1, "one")
println(t1._1) // 1
```

Most languages have a special keyword or type instance that's assigned to reference variables when there's nothing else for them to refer to.In Java, it's `null` which is a keyword, not an instance of a type. However, we cannot apply any method on null as it is keyword not a type. This means we don't have a value in a given situation. In Scala, we can use type system and use type checking to avoid `NullPointerExceptions`. This is used in Maps most often.

Chcek [state-capitals.sc](src/main/scala/basics/state-capitals.sc)

**Option** lets us express this situation without the null. Option is an abstract class with two concrete implementation `Some`, for when we have a value and `None` when we don't. The `Map.get` method returns `Option[T]` where T is type of value in Map object. Java's `Map.get` returns T or null. When you get `Option`, you can use `get` or `getOrElse` method. `get` method is dangerous, as it may return value or throw a `NoSuchElementException`. Scala's type-checking also ensures that by mistake if Option is returned, you don't end up calling a method on the type returned by `Some`.

A key point with Option is there are only two possible valid subtypes. There are no other subtypes of `Option` that would be valid. Scala has a keyword `sealed` for avoiding inheriting some classes.

`sealed abstract class Option[+A] ... {...}`

The sealed keyword tells the compiler that all subclasses must be declared in the same source file. `Some` and `None` are declared in the same file with `Option` in Scala library. This prevents additional subtypes of Option.

Scala has package concept that Java uses for namespaces. However, in Scala, filenames don't have to match the type names and the package structure does not have to match the directory structure. So, you can have packages in files independent of their physical location. Scala also supports a block-structured syntax for declaring package scope which is similar to namespace in C#.

Here, we can reference classes inside pkg2 as `pkg1.Class11` or with full paths like `com.example.pkg1.Class11`. There is one limitation that packages cannot be defined within classes and objects.

```scala
package com {
  package example {
    package pkg1 {
      class Class11 {
        def m = "m11"
      }
      class Class12 {
        def m = "m12"
      }
    }
    package pkg2 {
      class Class21 {
        def m = "m21"
        def makeClass11 = {
          new pkg1.Class11
        }
        def makeClass12 = {
          new pkg1.Class12
        }
      }
    }
  }
}
```

Scala allows importing classes from different packages easily. Another point is that we can import anywhere in source file. If we import inside a function, the imports are only visible inside the method.

```scala
import java.awt._  // import everything inside given package
import java.io.File // import only File class
import java.io.File._ // import static methods from File class
import java.util.{Map, HashMap} // import only two classes
``` 

Another point is that imports are relative, so if we import everything in a package, then we can import other classes relatively.

```scala
import scala.collection.mutable._
import collection.immutable._
```

If we have a library where we want to expose APIs that clients will import and use, then for that **package object** may be used.

Sometimes, if we have static methods inside JSON class which is inside json package, import statement would look like: `static import com.example.json.JSON.*` in Java. We want it to look pretty like `import com.example.json._`. For this we can have package object, which will export everything inside a package.

```scala
package com.example
package object json {
  class JSONObject {
  }
  def fromString(string: String): JSONObject = {...}
}
```

Scala supports generics with `[]` symbol. Java uses `<>`. When you see `sealed abstract class List[+A]`, the + in front of A means that List[B] is a subtype of List[A] for any B that is subtype of A. This is called *covariant typing*. If there is - in front of a type parameter, the relationship goes the other way: Foo[B] would be supertype of Foo[A], if B is subtype of A and the declarations is `Foo[-A]` which is called *contravariant typing*.

Scala also supports abstract types like Java.

[abstract-types](src/main/scala/basics/abstract-types.sc)

The same can be written with parameterized types.

```scala
abstract class BulkReader[In] {
  val source: In
    ...
}
class StringBulkReader(val source: String) extends BulkReader[String] {...}
class FileBulkReader(val source: File) extends BulkReader[File] {...}

```

Almost all operators are actually methods in Scala. For example, `1 + 2` is the same as `1.+(2)`. However, because of "infix" notation, we can drop the period and parentheses for single-argument methods. Similarly, a method with no arguments can e invoked without the period. This is called "postfix" notation. If we use `1 toString`, it will throw warning message. To use such postfix operations, we need to import `postfixOps`. Another option is we can pass another flag to compiler to enable the feature globally, `-language:postfixOps`.

```scala
1 toString // gives warning message
import scala.language.postfixOps
1 toString // works without warning
```

Backquotes (`) can be used to use reverved words. Like it can be used to invoke Java method that is reserved word in Scala.

```scala
java.net.Proxy.`type`()
```

In Scala, operators are also methods. This flexiblity of naming gives you the power to write libraries that feel like a natural extension of Scala itself. If the method takes no parameters, you can define it without parentheses. Callers must invoke the method without parentheses. If you add parenthese to definition, callers have option of adding parenthese or not. For example, `List.size` has no parentheses, so if you try `List(1,2,3).size()`, you'll get an error. When the method has side effects, parentheses are usually added, to signal caution to the reader that mutation might occur. If you use `-Xlint` option when invoking `scala` or `scalac`, you will get warning message if you define a method with no parenthese that performs side effects. It makes writing method chain calls more readable.

Check [no-dot-operator](src/main/scala/basics/no-dot-operator.sc)

In Scala, left-associative method invocations bind left-to-right order. Any method with a name that ends with a colon (:) binds to the right, while all other methods bind to the left. 

```scala
val list = List('b', 'c', 'd')
'a' :: list // List(a,b,c,d)
list.::('a')
```


Scala provides excellent support for creating DSL. For example, Scala test library is one of the DSL used for testing purpose.

In Scala, we can assign values as output of `if...else` statements. As if statements are so expressive, ternary conditional expressions are not suppored in Scala.

```scala
val configFile = new java.io.File("somefile")
val configFilePath = if(configFile.exists()) {
  configFile.getAbsolutePath()
} else {
  configFile.createNewFile()
  configFile.getAbsoluteFile()
}
```

[Basic For loop](src/main/scala/basics/basic-for.sc)

The expression `name <- names` is called a generator expression. We can also filter values from the for loop by guarding. Such expressions are called *guards*.

```scala
val dogBreeds = List("Doberman", "Yorkshire Terrier", "Dachshund", "Scottish Terrier", "Great Dane", "Portuguese Water Dog")
for (breed <- dogBreeds
  if breed.contains("Terrier")
) println(breed)
```

Check [double guards](src/main/scala/basics/double-guard-for.sc)

If you wanted to collect these filtered variablese and pass it to another par of the program, you can use `yield`.

```scala
val names = List("Piyush Patel", "Aniket Suthar", "Ishit Patel", "Pragnesh Mohan")

val filteredNames = for {name <- names
  if name.contains("Patel")
  if !name.startsWith("Piyush")
} yield name
```

Another useful feature of for comprehension is the ability to define values inside the first part of for expressions that can be used in the later expressions.

[scope for](src/main/scala/basics/scope-for.sc)

For comprehension also effectively manages extracting data from Option type variables.

[sccped-options](src/main/scala/basics/scoped-options.sc)

You use `<-` when you're iterating over a collection or other containere like an Option and extracting values. Scala for comprehension do not offer a `break` or `continue` feature. Other features make them unnecessary.

[While loop](src/main/scala/basics/while-loop.sc)

Scala while loop is similar to other programming languages. Do...while loop is also similar to other languages.

```scala
var count = 0
do {
  count += 1
  println(count)
} while(count < 10)
```

Scala offers all conditional operators with `&&` and `||` being short-cirtuited operations. In Java `==` compares object references only, in contrast Scala uses this symbol for logical equality but calls the `equals` method under the hood. The new method `eq` is available if you want to check object references like Java `==`.

Unlike Java, Scala doesn't have checked exceptions which are now regarded as an wrong design. There is also no throws clause on method declarations. There is `@throws` annotation that is useful for Java interoperability. Scala treats exception handling as another pattern match.

Check [try catch example](src/main/scala/objectoriented/TryCatch.scala)

When resources need to cleaned up, whether or not the resource is used successfully, put the cleanup logic in a finally clause.

Automatic resource management is a common pattern. There is a separate project *ScalaARM* for this.

Check [TryCatchArm](src/main/scala/basics/TryCatchArm.scala)

Here, we named object as `manage`. In this case we will use manage like a function. The `apply` method is different from other objects. Here R is a type of resource we'll manage. The `<:` means R is a subclass of something else, in this case a structural type with a `close():Unit` method. If we haven't seen structural types before, we could like all resources to implement a `Closable` interface that defines `close():Unit` method. Then we would have `R <: Closable`. Instead, structural tyeps let us use reflection and plug in any type that has a `close()` method. Reflect has a lot of overhead and structural types are a bit scary, so reflection is another optional feature, like postfix. So, we need to add import statement to tell compiler what we're doing. Here `manage` looks very much like built-in control structure with one argument list that creates the Source and a second argument list that is a block of code that works with the Source. The evaluation of the expression is delayed until the line `val res = Some(resource)` within manage. This is what the by-name parameter resource give us. Scala also supports *call-by-value* semantics. If we write `manage(Source.fromFile(fileName))` in a call-by-value context, the `Source.fromFile` method is called and the value it returns is passed to manage.

By deferring evaluation until the line `val res = Some(resource)` within apply, this line as follows.

```scala
val res = Some(Source.fromFile(fileName))
```

[By name parameters](src/main/scala/basics/call-by-name.sc)

When you want to evaluate an expression once to initialize a value, not repeatedly, but want to defer the invocation. This is especially useful when
 - expression is expensive (opening database connection) and we want to avoid the overhaul until the value is actually needed.
 - Improve startup times for modules by deferring work that isn't needed immediately.
 - A field in an object needs to be initialized lazily so that other initializations can happen first.
 
 ```scala
 object ExpensiveResource {
  lazy val resource: Int = init()
  def init(): Int = {
    0
  }
 }
 ```
 
 The `lazy` keyword indicates that evaluation should be deferred until the value is needed. The `lazy val` is different from method call in that the body is executed every time the method is invoked. For a lazy val, the initialization body is evaluated only once when the value is used for the first time. This one time evaluation is useless for mutable fields. Therefore, the lazy keyword is not allowed on *vars*. Lazy values are implemented withe a *guard*. When client code references a lazy value, the reference is intercepted by the guard to check if initialization is required. This guard is really only essential the first time the value is referenced so that the value is initialized first before the access is allowed to proceed. There is no easy way to eliminate these checks for subsequent calls. So, lazy values incur overhead that eager values don't.
 
 ### Enumerations
 
 We can have categorical values in enumerations. While enumerations are built-in part of many programming languages. Enumerations has no special syntax, only define an object that extends `Enumeration` class and follow idioms.
 
 ```scala
 object Breed extends Enumeration {
 // type Breed is an alias that lets us reference Breed instead of Value.
  type Breed = Value
  val doberman = Value("Doberman Pinscher")
  val yorkie = Value("Yorkshire Terrier")
  val scottie = Value("Scottish Terrier")
  val dane = Value("Great Dane")
  val portie = Value("Portuguese Water Dog")
 }
 import Breed._
 println("ID\tBreed")
 // call `values` method to work with enumerations as collection
 for (breed <- Breed.values)
 // IDs are incremented and assigned automatically from 0
  println(s"${breed.id}\t$breed")
  println("\nJust Terriers:")
  Breed.values filter (_.toString.endsWith("Terrier")) foreach println
 def isTerrier(b: Breed) = b.toString.endsWith("Terrier")
 
 println("\nTerriers Again??")
 Breed.values filter isTerrier foreach println
 
 ```
 
[days enuemration](src/main/scala/basics/days-enumeration.sc)

Enumerations are limited in use where you know in advance the exact set of values to define. Clients can't add more values. Instead, case classes are used when an "enumeration of values" is needed. They are little bit heavy-weight but offer flexiibility to add methods and fields, and to perform pattern matching on them. Client code can add more case classes to a base set that your library defines when useful.

When using **interpolated strings**, the expression should return String. If `toString` method doesn't exists and expression cannot be converted to String, an error is returned. When using interpolated strings, to write a literal dollar sign $, use two of them, $$.

```scala
val gross = 100000F
val net = 64000F
val percent = (net / gross) * 100
// two percent sign, %%, to print a literal percent sign
// two US dollar sign, to print  dollar sign
println(f"$$${gross}%.2f vs. $$${net}%.2f or ${percent}%.1f%%")

```

We can still format strings using `printf` style formatting with Java static method `String.format`. 

```scala
val s = "%02d: name = %s".format(5, "Piyush Patel")
```

## Traits: Interfaces and Mixins

Java has interfaces which let you declare but not define methods. Scala replaces interfaces with traits. Simply, traits can be thought of as interfaces that also give you the option of defining the methods you declare. Traits can also declare and optionally define instance fields (not only static), you can declare and optionally define type values. Traits also enable true composition of behavior (mixins) that is not possible in Java before Java 8.

[Traits example](src/main/scala/objectoriented/traits.sc)

To mix in traits, we use `with` keyword. We can mix in as many as we want. In the following example, we modify the behaviour of work by injecting logging. 

[Logging with traits](src/main/scala/objectoriented/logging.sc)

We are not chaning its contract with clients. If we need multiple instances of *ServiceImportante* with *StdoutLogging*, we could declare a class.

```scala
class LoggedServiceImportante(name: String) extends ServiceImportante(name) with StdoutLogging {...}
```

We need to use **override** keyword when we override parent class methods.

## Pattern Matching

Scala's pattern matching is similar to `switch..case` statements in other languages. It can include types, wildcards, sequences, regular expressions and even deep inspections of an object's state. Pattern matching can be used in several code contexts.

[Simple match example](src/main/scala/objectoriented/match-boolean.sc)

When writing *PartialFunctions*, we aren't required to match all possible values because they are intentionally partial. Matches are eager so more specific clauses must appear before less specific clauses. The default clause must be the last one. Matching on floating point literal is a bad idea, as rounding errors mean two values that appear to be the same often differ in the least significant digits. We can also replace placeholder variables with an underscore sign.

```scala
case _: Int => "other int: " + x
```

The compiler assumes that a term that starts with a capital letter is a type name, while a term that begins with a lowercase letter is assumed to be the name of a variable that will hold an extracted or matched value.

[Match surprise](src/main/scala/objectoriented/match-surprise.sc)

In case clauses, a term that begins with a lowercase letter is assumed to be the name of a new variable that will hold an extracted value. To refer to a previously defined variable, enclose it in backticks.

If we want to match more than one type of variable in a single case clause, we can use pipe symbol to match multiple types.

```scala
case _:Int | case _: Double => "a number: " + x
```

The Scala library has an object called *Nil* for lists and it matches all empty sequences.

[Pattern match with List](src/main/scala/objectoriented/match-list.sc)

[Pattern match with Tuples](src/main/scala/objectoriented/match-tuple.sc)

Similar to for comprehension guards, we can have match guards which will match based on logical condition.

```scala
for (i <- Seq(1,2,3,4)) {
  i match {
    case _ if i%2 == 0 => println(s"even: $i")
    case _ => println(s"odd: $i")
  }
}
```

We can also do pattern matching on case classes.

[Pattern matching on Case classes](src/main/scala/objectoriented/match-deep.sc)

[Deep matching with Tuples](src/main/scala/objectoriented/match-deep-tuple.sc)

A case class gets a companion object that has a factory method named `apply`, which is used for construction. Similarly, there is another method `unapply`, used for extraction or destruction. This extraction method is also used for pattern-match expressions. Scala looks for `Person.unapply` and `Address.unapply` and calls them. All unapply methods return an `Option[TupleN[...]]` where N is number of values that can be extracted from the object.

```scala
person match {
  case Person("Alice", 25, Address(_, "Chicago", _)) => ...
}
```

The companion object for Person class looks as below:

```scala
object Person {
  def apply(name: String, age: Int, address: Address) = new Person(name, age, address)
  def unapply(p: Person): Option[Tuple3[String, Int, Address]] = Some((p.name, p.age, p.address))
    ... ...
}
```

We do  not need to expose all the fields of the instance if we don't want to. The compiler then extracts those tuple elements for comparison with literal values. To gain performance benefits, Scala 2.11.1 relaxed the requirement that unapply method return an Option[T]. It can now return any type if it has following methods.

```scala
def isEmpty: Boolean
def get: T
```

We can use tuple literal syntax to represent return type.


```scala
// These are all same
val t1: Option[Tuple3[String, Int, Address]] = 
val t2: Option[(String, Int, Address)] 
```

Person.unapply and TupleN.unapply already know how many things are in their instances, three and N. If we want to support non-empty collection of arbitrary length, Scala defines a special singleton object named `+:.`. It has just one method, the `unapply` method the compiler needs for extraction.


[Infix notation](src/main/scala/objectoriented/infix.sc)

For lists, if you want to process the sequence elements in reverse, the library object `:+` allows you to match on the end elements and work backward.

[Match in reverse sequence](src/main/scala/objectoriented/match-reverse-seq.sc)

Nils come first and the methods bind to the left. We can check forward sequence and reverse sequence methods.


```scala
def seqToString[T](seq: Seq[T]): String = seq match {
  case head +: tail => s"$head +: " + seqToString(tail)
  case Nil => "Nil"
}

def reverseSeqToString[T](l: Seq[T]): String = l match {
  case prefix :+ end => reverseSeqToString(prefix) + s" :+ $end"
  case Nil => "Nil"
}
```

If you want more flexibility to return a sequence extracted items, rather than a fixed number of them, we can use `unapplySeq` method.

[Matching with Seq unapplySeq method](src/main/scala/objectoriented/match-seq-unapplyseq.sc)

[Matching without Seq unapplySeq method](src/main/scala/objectoriented/match-seq-without-unapplyseq.sc)

Working with sliding windows is so useful that Seq gives us two methods to create them.

```scala
val seq = Seq(1,2,3,4,5)
val slide2 = seq.sliding(2) // create two element lists with sliding of 1 element
slide2.toSeq //  converts the iterator into a Stream, a lazy list that evaluates its head eagerly, but only evaluates the tail elements on demand.
slide2.toList // evalutes the whole iterator eagerly, creating a List
seq.sliding(3,2).toList // create three element lists with sliding of 2 elements
```

#### Matching on variable argument lists

Suppose we want to create a tool to interoperate with SQL and want a case class to represent the WHERE foo IN (val1, val2, ...) SQL clause.

[Match variable argument list](src/main/scala/objectoriented/match-vararglist.sc)

Scala allows matching on regular expressions.

[Match regex](src/main/scala/objectoriented/match-regex.sc)

If you want to extract values from an object and also want to assign variable to the whole object itself.

[Match deep to objects](src/main/scala/objectoriented/match-deep2.sc)

Pattern matching can also work on types.

[Match types](src/main/scala/objectoriented/match-types.sc)

To solve, type erasure issues, we can use collection first and then use a nested match on the head element to determine the type.

[Match types to overcome type erasure](src/main/scala/objectoriented/match-types2.sc)

#### Sealed Hierarchies

If declared class is sealed, the only allowed subtypes must be defined in the same file.

[Sealed classes and hierarchy](src/main/scala/objectoriented/http.sc)

When pattern matching on an instance of a sealed base class, the match is exhaustive if the case clauses cover all the derived types defined in the same source file. Avoid using *sealed* if the type hierarchy is at all likely to change. Although, we can use Enumeration values for above HttpMethod subclasses. Avoid enumerations when pattern matching is required. The compiler can't tell if the matches are exhaustive. Pattern matching can be used even for defining values including in for comprehension.

```scala
val Person (name, age, Address(_, state, _)) = Person("Dean", 29, Address("1 Scala Way", "CA", "USA"))
// above statement results in extraction of values for name, age, state

val head +: tail = List(1,2,3) // head = 1
val head1 +: head2 +: tail = Vector(1,2,3) // head1=1, head2=2, tail = Vector(3)

val Seq(a,b,c) = List(1,2,3,4) // MatchError
```


We can also use pattern matching in if clauses.

```scala
val p = Person("Dean", 29, Address("1 Scala Way", "CA", "USA"))
if(p == Person("Dean", 29, Address("1 Scala Way", "CA", "USA"))) "yes" else "no"
```

There is an internal function `$eq$eq` that's used for the `==` test. 
If we have a function that takes a sequence of integers and returns the sum and the count of elements in a tuple, we can have a function as below.

```scala
def sum_count(ints: Seq[Int]) = (ints.sum, ints.size)
val (sum, count) = sum_count(List(1,2,3,4,5))
```

Another use of pattern matching and `case` clauses is to make function literals of complex arguments easiers to use:


```scala
case class Address(street: String, city: String, country: String)
case class Person(name: String, age: Int)
val as = Seq(
  Address("1 Scala Way", "CA", "USA"),
  Address("2 Clojure Lane", "Othertown", "USA")
)
val ps = Seq(
  Person("Buck Trends", 29),
  Person("Clo Jure", 28)
)
val pas = ps zip as

// Ugly way 
pas map { tup =>
  val Person(name, age) = tup._1
  val Address(street, city, country) = tup._2
  s"$name (age: $age) lives at $street, $city in $country"
}

// Nicer way with partial function
pas map {
  case (Person(name, age), Address(street, city, country)) => s"$name (age; $age) lives at $street, $city in $country"
}

```

Pattern matching is a powerful tool for extracting data inside data structures. We can control extracting information using pattern matching. We can customize `unapply` method to control the state exposed. 

## Implicits

Implicits are a powerful feature in Scala. They are used to reduce boilerplate, to simulate adding new methods to existing types, and to support the creation of domain-specific languages (DSLs). Implicits are controversial because they are nonlocal in the source code. We can import implicit values and methods into the local scope, except for those that are imported automatically through `Predef`. Once in scope, an implicit might be invoked by the compiler to populate a method argument or to convert a provided argument to the expected type. When reading code it becomes difficult to understand. When an implicit argument is omitted, a type-compatible value will be used from the enclosing scope, if available. Otherwise, a compiler error occurs.

```scala
def calcTax(amount: Float)(implicit rate: Float): Float = amount * rate // Here rate is implicit
implicit val currentTaxRate = 0.08F // this value will be used for rate
val tax = calcTax(50000F) // 4000.0
```

If we have a situation where we want to populate several fields, we can also use implicit methods.

[Implicit arguments](src/main/scala/objectoriented/implicit-args.sc)

`Predef` defines a method called `implicitly`. Combined with a type signature addition, it provides a useful shorthand way of defining method signatures that take a single implicit argument, where that argument is a parameterized type.

[Example of implicitly](src/main/scala/objectoriented/implicitly-args.sc)

Whatever argument is passed to the method for the implicit argument is resolved by *implicitly*. It's important to use implicits wisely and sparingly. 

#### Implicit uses

Implicit argument list is used to pass an `ExecutionContext` to `Future.apply` method. This way it is useful for **passing execution contexts**.

```scala
apply[T](body: => T)(implicit executor: ExecutionContext): Future[T]
```

Besides passing contexts, implicit arguments can be used to **control capabilities**. For example, an implicit user session argument might contain authorization tokens that control whether or not certain API opeartions can be invoked on behalf of the user or to limit data visibility. Suppose you are constructing a menu for a user interface and some menu items are shown only if the user is logged in, while others are shown only if the user isn't logged in.

```scala
def createMenu(implicit session: Session): Menu = {
  val defaultItems = List(helpItem, searchItem)
  val accountItems = if(session.loggedin()) List(viewAccountItem, editAccountItem) else List(loginItem)
  Menu(defaultItems ++ accountItems)
}

```

Suppose we have a method with parameterized types and want to **constrain the allowed types** that can be used for the type parameters. If the types we want to permit are all subtypes of a common supertype, we can use OOP and avoid implicits. Earlier, we saw such implementation in [manage object](src/main/scala/basics/TryCatchArm.scala). However, this technique doesn't help when there is no common superclass. For such situation, we can use implicit argument to limit the allowed types. Scala Collections API uses this to solve design problem.

Implicit can also be used for implicit conversions.
[Implicit conversions](src/main/scala/objectoriented/implicit-conversions.sc)

The convention is to put implicit values and conversions into a special package named `implicits` or an object named `Implicits` except for those defined in companion objects. This is for clarity.

Scala has several implicit wrapper types for Java types like String and Array. For example, methods below appear to be working on String, but they are implemented by WrappedString

```scala
val s = "Scala is fun"
s.reverse
s.capitalize
s.foreach(c => print(s"$c-"))
```

The implicit conversion for the built-in "Wrapped" types are always in scope. They are defined in `Predef`. 

We can define String interpolation methods using implicits. For example,

```scala
val name = ("Buck", "Trends")
println(s"Hello, ${name._1} ${name._2}")
```

When compiler sees expression like `x"foo bar"`, it looks for an `x` method in `scala.StringContext`. So, it will be translated to this:

```scala
StringContext("Hello, ", " ", "").s(name._1, name._2)
```

The arguments to `StringContext.apply` are the parts around the `${...}` expressions.

[Custom String interpolator](src/main/scala/objectoriented/custom-string-interpolator.sc)

Implicit classes must be defined inside objects to limit their scope to limit their scope. The zip method on collections is a way to line up the values between two collections. The result of zip will size equal to the minimum of the two collections.

```scala
val keys = List("a", "b", "c", "d")
val values = List("A",123, 3.14159)
val keysValues = keys zip values // this will have size 3
```

Scala support OOP inheritance and polymorphism. However, sometimes it's required that we implement required methods in our defined class only and don't carry around the burden defined in the super classes. Then, Scala supports mixin features to implement required behavior. 

**Type classes** help us avoid the temptation of creating "kitchen-sink" abstractions, like Java's Object because we can add behavior on ad-hoc basis. Type classes in Haskell define the equivalent of an interface, then concrete implementations of it for specific types. The Type Class Pattern in Scala adds the missing interface piece that Java does't provide.

[toJSON type class](src/main/scala/objectoriented/toJSON-type-class.sc)

Scala doesn't allow *implicit* and *case* keywords together. We used this method to add methods to existing classes. This capability is *extension methods*. This is also called *ad hoc polymorphism* because the polymorphic behavior of `toJSON` is not tied to the type system.

### Disadvantages of implicits

The extra code involved in defining implicits is extra work developer has to do and the compiler must work harder to process implicits. A project that uses implicits heavily is slow to build. Implicit conversions also  incur extra runtime overhead due to layers of indirection from the wrapper types

Here are some **tips**

Always specify the return type of an implicit conversion method. If you define a + method on a type and attempt to use it on an instance that actually isn't of the correct type, the compiler will instead call the `toString` on the instancee and then call String + operation. So, you may get mysterious error about a String being the wrong type.

**When Scala looks for implicits**, it follows a sequence of sophisticated rules for the search.

- Any type-compatible implicit value that doesn't require a prefix path. In other words, it is defined in th same scope, such as within the same block of code, within the same type, within the companion object (if any), and within parent type.
- An implicit value that was imported into the current scope.
- In some cases, several possible matches are type compatible. The most specific match wins. For example, if the type of an implicit argument is Foo, then an implicit value in scope of type Foo will be chosen over an implicit value of type AnyRef.
- If two or more implicit values are ambiguous, such as they have the same specific type, it triggers a compiler error.

## Scala's Built-in Implicits

Scala library has more than 300 implicit methods, values and  types. Any of the companion objects for `AnyVal` types have widening conversions, such as converting an Int to a Long. They are like `toX` methods. BigInt and BigDecimal have converters from many of the AnyVal types and from the corresponding Java implementations. Option can be converted to a list of zero or one items. Scala uses Java's types including `Array[T]` and `String`. There are corresponding `ArrayOps[T]` and `StringOps` types that provide the operations commonly defined for all Scala collections. Most of these conversions are defined in `Predef`. Some of these definitions have the `@inline` annotation which encourages the compiler to try especially hard to inline the method call. The `scala.collection.convert` package has several traits that add conversion methods between Java and Scala collections. We can use methods like `asJava` or `asScala` to convert Java to Scala and vice-a-versa.

Sorting on collections is a common task. There are many implicits for `Ordering[T]`. The `scala.concurrent.duration` package provides useful ways of defining time durations. Finally `Process` supports operating systems processes analogous to running UNIX shell commands.


[Functional Programming](notes/README2.md)

[Testing Scala code](notes/TESTING.md)

