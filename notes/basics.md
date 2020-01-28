# Basics of Programming

In Scala values are defined using `val` keyword and variables are defined using `var` keyword. The datatypes are specified after the variables name.

```scala
val x: Int = 5 // this is immutable data structure
var x: Int = 2
```

With `val` datatypes are not mandatory as Scala can infer the datatypes.
In Scala, everything is an expression. We can write like iterative programming syntax of Java. For example a while loop as follows.

```scala
var i = 0
while (i < 10) {
  println(i)
  i += 1
}
```

However, this syntax is discouraged in Scala to promote functional style with immutability. Semicolons are optional as long as every expression is on a separate line.
In Scala, `if...else` is an expression so, we can assign the value of result of `if...else` just like  tertiaray opeartion in Java.

```scala
val even = if (x % 2 == 0) true else false
```

Even functions are evaluated and the last statement becomes the return type. The `return` keyword is also optional in Scala.

```scala
def add2(a: Int, b: Int): Int = {
  if (a == 2) a + b else b + 2
}
```

In Scala, everything is an object. For example when we calculate 1 + 2. It is actually a method call on object like `1.+(2)`.
We define an array like

```scala
val greetStrings = new Array[String](3) // create array with 3 elements
greetStrings(0) = "Hello"
greetStrings(1) = ", "
greetStrings(2) = "World"

// for loop in scala
for (i <- 0 to 2)
  println(greetStrings(i))
```

When we call `greetStrings(i)`, it actually calls `apply` method on that variable like `greetStrings.apply(i)`. When we assign a value, that calls `update` method. For example `greetStrings(0) = "Hello"` is transformed into `greetStrings.update(0, "Hello")`. We also use `apply` method for initializing complex data structures like below.

```scala
val numNames = Array("zero", "one", "two") // transforms to below
val numNames2 = Arary.apply("zero", "one", "two") 
```

## Lists

Scala automatically infers datatypes. Scala Lists are like Java lists but they are immutable.

```scala
val oneTwoThree = List(1,2,3)
val oneTwo = List(1,2)
val threeFour = List(3,4)
val oneTwoThreeFour = oneTwo ::: threeFour // this returns new list, does not mutate oneTwo
```

We can use prepend operator `::` which is pronounced as "cons". It prepends head element to the list.

```scala
val twoThree = List(2,3)
val oneTwoThree = 1 :: twoThree // List(1,2,3)
```

The expression `1 :: twoThree` is a method on right operand. If method name ends in a colon, the method is invoked on the right operand. Therefore, `1 :: twoThree` is equivalent to `twoThree.::(1)`.
The shorthand way to specify an empty liist is `Nil`. We can construct a list using *cons* operator.

`val oneTwoThree = 1 :: 2 :: 3 :: Nil`

In Scala, appending to a list is less efficient. Instead, you should prepend elements and then call `reverse` on the generated list.


- `List()` or `Nil`: empty List 
- `List("cool", "tools")`: Creates a new `List[String]` with two elements.
- `val thrill = "Will" :: "fill" :: "until" :: Nil`: Creates a list 
 - `List("a", "b") ::: List("c", "d")`: Concatenates two lists
 - `thrill(1)`: returns the element at index 1 ("fill")
 - `thrill.count(s => s.length == 4)`: counts the number of string elements in thrill that have length 4 (returns 2)
 - `thrill.drop(2)`: returns the thrill list without its first 2 elements(`List("until")`)
 - `thrill.dropRight(2)`: returns thrill list without the rightmost 2 elements
 - `thrill.exists(x => x == "until")`: Determines whether a string exists in thrill that has the value "thrill" (true)
 - `thrill.filter(x => x.length == 4)`: returns list with elements that have length 4
 - `thrill.forall(s => s.endsWith("l"))`: indicates whether all elements in the thrill list end with letter "l" (true)
 - `thrill.foreach(s => println(s))`: executes `println` function for each element.
 - `thrill.foreach(println)`: shorthand syntax
 - `thrill.head`: returns the first element
 - `thrill.init`: returns a list of all but the last element
 - `thrill.isEmpty`: checks if list is empty
 - `thrill.last`: returns the last element of the list
 - `thrill.length`: returns the number of elements in the list
 - `thrill.map(s => s + "y")`: returns a list with "y" added to each element in list.
 - `thrill.mkString(",")`: Make s String with the elements of the list
 - `thrill.filterNot(s => s.length == 4)`: returns a list of all elements except those that have length 4
 - `thrill.reverse`: returns a list with all elements of list in reverse order
 - `thrill.sort((s, t) => s.charAt(0).toLower < t.charAt(0).toLower)`: returns a list with all elements of the list in alphabetical order of first character lowercased.
 - `thrill.tail`: returns list minus its first element

## Tuples

Tuples are immutable. Tuples can contain different types of elements. If you need ot return multiple objects from a method, in Scala you can return a Tuple instead of an object. You can access the elements of a tuple using `_i` syntax.

```scala
val pair = (99, "Luftballoons")
println(pair._1)
```

In Scala, arrays are always mutable. Scala provides mutable and immutable alternatives for sets and maps. In Scala, we extend or mixin traits unlike Java where we only implement interfaces.

```scala
var jetSet = Set("Boeing", "Airbus")
jetSet += "Lear"
println(jetSet.contains("Cessna"))
```

We can create Set like we do using factory method named `apply` on a Set companion object. To add new element into a set, we call `+` on the set with new element. If you want mutable set, you can import mutable.

```scala
import scala.collection.mutable
val movieSet = mutable.Set("Hitch", "Poltergeist")
movieSet += "Shrek"
```

Above code is similar to `movieSet.+=("Shrek")`.

**Map** class has similar class hierarchy.

```scala
import scala.collection.mutable

val treasuremap = mutable.Map[Int, String]()
treasureMap += (1 -> "Go to island.")
treasureMap += (2 -> "Find big X on ground.")
treasureMap += (3 -> "Dig.")
println(treasureMap(2))
```

The `->` method returns a two-element tuple containing the key and value. So, `1 -> "Go to island"` is equivalent to `(1).->("Go to island")`. For immutable map, no import is necessary as immutable is the default map.

Scala's assert method checks the passed Boolean and if it is false, throws AssertionError.

```scala
val x = 2
assert(x == 3) // throws AssertionError
```

Reading lines from a file

```scala
import scala.io.Source

if(args.length > 0) {
  for (line <- Source.fromfile(args(0)).getLines())
    println(line.length + " " + line)
}
else 
  Console.err.println("Please enter filename")
```

The `getLines` method returns `Iterator[String]` which provides one line on each iteration excluding the endline character.

In functional programming, we try to avoid using `var`. For example, finding a string with maximum length will require to iterate through it and you may change the max to new value.

```scala
var maxWidth = 0
for (line <- lines) 
  maxWidth = maxWidth.max(widthOfLength(line))
```

The same in functional programming could be written as below.

```scala
val longestLine = lines.reduceLeft(
  (a,b) => if (a.length > b.length) a else b
)
```

## Classes and Objects

A class is a blueprint for objects. Inside class definition, you place fields and methods which are collectively called members. The fields hold the state or data of an object whereas the methods use that data to do the computational work of the object. Every instance of an object gets its own instance variables.

*Private* fields can only be accessed by methods defined in the same class. In Scala, to make a member public, you do not need to explicitly specify any access modifier because *public* is the default access level.

```scala
class ChecksumAccumulator {
  def add(b: Byte): Unit = {
    sum += b
  }

  def checkSum(): Int = ~(sum & 0FF) + 1
  }
}
```

In Scala, you don't need to explicitly use `return` statement to return a value. The last evaluated expression is returned. You can also leave off the curly braces if a method computes only a single result expression. If the expression is short, it can even be placed on the same line as the `def` itself. The `add` method is executed for its side effects. Such methods are known as a *procedure*.

Classes in Scala cannot have static members. Instead, Scala has **singleton objects**. It is like class definition, except instead of the keyword `class`, you use `object`. When a singleton object shares the same name with a class, it is called the class's **companion object**. You must define both the class and its companion object in the same source file. The class is called *companion class* of singleton object. A class and its companion object can access each other's private members.

```scala
import scala.collection.mutable

object ChecksumAccumulator {
  private val cache = mutable.Map.empty[String, Int]

  def calculate(s: String): Int = 
    if (cache.contains(s)) // check if checksum exists in cache
      cache(s) // if yes, get the value
    else {
      val acc = new ChecksumAccumulator
      for (c <- s)
        acc.add(c.toByte)
      val cs = acc.checkSum() // calculate checksum
      cache += (s -> cs) // add calculated value to cache
      cs
    }
}
```

Singleton objects extend a superclass and can mix in traits. Singleton objects cannot take parameters. It is initialized the first time some code accesses it. A singleton object that does not share the same name with a companion class is called as standalone object. These are used for collecting utility methods together or defining an entry point to a Scala application.

To run a Scala program, you must supply a name of a standalone singletone object with a main method that takes on parameter and has return type of Unit.

```scala
import ChecksumAccumulator.calculate

object Summer {
  def main(args: Array[String]) = {
    for (arg <-  args) 
      println(arg + ": " + calculate(arg))
  }
}
```

Scala implicitly imports members of `java.lang` and `scala` as well as members of object named `PreDef` into Scala source file. `PreDef` contains many useful methods like `println`, `assert`, etc. Scala allows you to name source file whatever you want, no matter what Scala classes or code you put in them unlike Java. In Java, you have to name the file based on the name of the public class in that file. Scala distribution includes a Scala compiler daemon `fsc`. 

Scala provides a trait, `scala.App` where you don't need to specify `main` method.

```scala
object FallWinterSpring extends App {
  for (season <- List("fall", "winter", "spring"))
    println(season + ": " + calculate(season))
}
```

## Data Types

```scala
val hex = 0x5
val hex2 = 0x00FF
val decimal = 31
val tower = 35L // long
val little: Short = 367
val littler: Byte = 38
val big = 1.2345
val bigger = 1.2345e1 // double
val little = 1.2345f // float
val anotherDouble = 3e5
val a = 'A'
val d = '\u0041'
```

Scala provides **string interpolation** for easy string formation. For example `s"The answer is ${6 * 7}` or `s"Name is ${name}` replaces name value with variable name's value.
Operators are methods. We can call methodss like `s indexOf 'o'` with spaces. 

```scala
val s = "Hello, World"
s indexOf ('o', 5)
```

When we write `s indexOf 'o'`, `indexOf` is an operator. This is *infix* operator notation. Scala has two other operator notations: prefix and postfix. In prefix, you put method name before the object on which you are invoking the method (`-7`). In postfix, you put the method after the object (`7 toLong`). Thses are unary operators. Postfix operators are methods that take no arguments when they are invoked without a dot or parentheses.

Scala have arithmetic, relational and logical operations like Java. Like Java, Scala offers short circuited operations. If you want to evaluate the right hand side no matter what, use & and | instead. All Scala methods have a facility for delaying the evaluation of their arguments. This facility is called *by-name parameters*. Scala offers bit wise operations too.

```scala
1 & 2 // AND
1 | 2 // OR
1 ^ 3 // XOR
~1 // complement
```

Scala integer types also offer three shift methods: shift left (<<), shift right (>>), and unsigned shift right (>>>).

If you want to compare two objects for equality, you can use either `==` or its inverse `!=`. In Scala, you use `==` to compare both primitive and reference typess.

```scala
List(1,2,3) == List(1,2,3) // true
1 == 1.0 // compare different objects
List(1,2,3) == "hello" // false
List(1,2,3) == null // false
```

In Java `==` is used to check reference equality for objects. In Scala, the same is provided by `eq` method to verify if both objects point to the same reference. Its opposite is `ne`.

With Scala's basic types, we can invoke many methods via **implicit conversions**. Each basic type has a rich wrapper that provides additional methods. Rich wrappers are in `scala.runtime` package like `scala.runtime.RichInt`.

## Functional objects

These are objects that do not have any mutable state. Let's experiment with example of a rational number expressed as a ratio `n/d`.
First think of how client programmers will create a new Rational object. We want to make Rational objects immutable. So, we need to provide data when the object is constructed.

```scala
class Rational(n: Int, d: Int)
```

In Scala, if a class doesn't have a body, you don't need to specify empty curly braces. Scala with gather up the two parameters and create a primary constructor that takes two parameters. Scala compiler will compile any code you place in the class body which is not part of a field or a method definition into the primary constructor. For example,

```scala
class Rationla(n: Int, d: Int) {
  println("Created " + n + "/" + d)
}
```

By default, class Rational inherits the implementation of `toString` defined in class `java.lang.Object`. You can override the default implementation by adding `toString` to class.

```scala
override def toString = n + "/" + d
```

For Rational numbers, the denominator should not be zero. This could be approached using *precondition* of the primary constructor. We can use `require` method to ensure that the condition matches.

```scala
class Rational(n: Int, d: Int) {
  require(d != 0)
  override def toString = n + "/" + d
}
```

Now, we look into supporting `add` method for two rational numbers.

```scala
def add(that: Rational): Rational = 
  new Rational(n * that.d + that.n * d, d * that.d)
```

Now, we cannot access `that.n` and `that.d`. To access them, we have to make them fields.

```scala
class Rational(n: Int, d: Int) {
  require(d != 0)
  val numer: Int = n
  val denom: Int = d
  override def toString = numer + "/" + denom
  def add(that: Rational): Rational =
    new Rational(
      numer * that.denom + that.numer * denom, denom * that.denom
    )
}
```

Now, the numerator and denominator values are accessible form outside the object.

```scala
val r = new Rational(1, 2)
r.numer // 1
r.denom // 2
```

The keyword `this` refers to object instance on which method was invoked.

```scala
def lessThan(that: Rational) = 
  this.numer * that.denom < that.numer * this.denom
```

In Scala, constructors other than primary constructor are called *auxiliary constructors*. For example, add a constructor to create 5/1 like rational numbers with only one argument and makes rational number with denominator set to 1.

```scala
def this(n: Int) = this(n, 1)
```

Now, rational number can be constructed like `new Rational(3)`. The first statement in every auxiliary constructor in every Scala class will have the form `this(...)`. The invoked constructor can be primary constructor or auxiliary constructor. The primary constructor is thus the single point of entry of a class.

In above implementation, the numerator and denominator can be larger than needed. For example, 66/42 could be normalized to 11/7. To normalize this way, you need to divide the numerator and denominator by their GCD.

```scala
class Rational(n: Int, d: Int) {
  require(d != 0)

  private val g = gcd(n.abs, d.abs)
  val numer = n / g
  val denom = d / g

  def this(n: Int) = this(n, 1)

  def add (that: Rational): Rational = 
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )
  
  override def toString = numer + "/" + denom

  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)
}
```

An *initializer* is the code that initializes a variable like 'n/g'. Scala compiler will place initializer code into the primary constructor.

We can define scala methods with symbols. So, it would be nice if we could perform something like `x + y` for adding two rational numbers. So, we can replace the method name `add` with `+` and we define method `*`.

```scala
def + (that: Rational): Rational =
  new Rational(
    numer * that.denom + that.numer * denom, denom * that.denom
  )

def * (that: Rational): Rational =
  new Rational(numer * that.numer, denom * that.denom)
```

Now, you can do following operations.

```scala
val x = new Rational(1,2)
val y = new Rational(2,3)
x + y
x.+(y)
```

In Scala, constants are named as camelcase with first character uppercase.
Now, we may want to multiply or add integer values to a rational number. Following class shows complete class.

```scala
class Rational(n: Int, d: Int) {
  require (d != 0)

  private val g = gcd(n.abs, d.abs)
  val numer = n / g
  val denom = d / g

  def this(n: Int) = this(n, 1)

  def + (that: Rational): Rational =
    new Rational(
      numer * that.denom + that.numer * denom, denom * that.denom
    )
  
  def +(i: Int) = Rational =
    new Rational(numer + i * denom, denom)

  def - (that: Rational): Rational =
    new Rational(
      numer * that.denom - that.numer * denom, denom * that.denom
    )

  def - (i: Int): Rational =
    new Rational(numer - i * denom, denom)

  def * (that: Rational): Rational =
    new Rational(numer * that.numer, denom * that.denom)

  def * (i: Int): Rational =
    new Rational(numer * i, denom)

  def / (that: Rational): Rational =
    new Rational(numer * that.denom, denom * that.numer)

  def / (i: Int): Rational =
    new Rational(numer, denom * i)

  override def toString = numer + "/" + denom

  private def gcd(a: Int, b: Int): Int = 
    if(b == 0) a else gcd(b, a % b)
}
```

With above class implementation, we can do following actions.

```scala
val x = new Rational(2, 3)
x * x
x * 2
```

We can do `x * 2` but we cannot swap the operands like `2 * r`. This will be method call on 2 which is integer. For this, we create implicit conversion that automatically converts integers to rational numbers when needed.

```scala
implicit def intToRational(x: Int) = new Rational(x)
```

With this defined in scope, compiler automatically converts integer to rational number.

## Control Structures

```scala
val filename = 
  if (!args.isEmpty) args(0) else "default.txt"
```

This allows us to use `val` instead of `var`. Scala has `while` and `do..while` loops. It is recommended to avoid these loops as they encourage use of `var`.

Scala's for expression enables common iterating through a sequence of integers and advanced iterate over multiple collections of different kinds.

```scala
val filesHere = (new java.io.File(".")).listFiles
for (file <- filesHere) 
  println(file)

for (i <- 1 to 5)
  println(i)

for (i <- 1 until 5)
  println(i)
```

We can also apply filters in the for expression. We can also include multiple filters.

```scala
val filesHere = (new java.io.File(".")).listFiles
for (file <- filesHere if file.getName.endsWith(".scala"))
  println(file)

for (
  file <- filesHere
  if file.isFile
  if file.getName.endsWith(".scala")
) println(file)
```

If you add multiple `<-` clauses, you will get nested loops. Scala compiler will not infer semicolons while inside parentheses.

```scala
for (
  file <- filesHere
  if file.getName.endsWith(".scala")
  line <- fileLines(file)
  if line.trim.matches(pattern)
) println(file + ": " + line.trim)
```

You can also generate a value to remember for each iteration using `yield` keyword. For example, below code finds the `.scala` files and stores them in an array.

```scala
def scalaFiles = 
  for {
    file <- filesHere
    if file.getName.endsWith(".scala")
  } yield file
```

The resulting collection is based on the kind of collections processed in iteration clauses.

```scala
val forLineLengths = 
  for {
    file <- filesHere
    if file.getName.endsWith(".scala")
    line <- fileLines(file)
    trimmed = line.trim
    if trimmed.matches(".*for.*")
  }  yield trimmed.length
```

In Scala, you throw exception just like you do in Java. However, `throw` is an expression that has a return type.

```scala
val half = 
  if (n % 2 == 0)
    n / 2
  else
    throw new RuntimeException("n must be even")
```

Catching an exception is also like Java using pattern matching.

```scala
try {
  val f = ne wFileReader("input.txt")
} catch {
  case ex: FileNotFoundException =>
  case ex: IOException =>
} finally {

}
```

You can use `@throws` annotation to indicate the method throws exception. Scala does not require you to catch checked exceptions or declare them in a throws clause. In Scala, instead of `try..catch..finally`, you can use *loan pattern* to achieve same concisely.
Pattern matching is a replacement for Java's switch statements. It matches the first pattern and executes its code.

```scala
val friend = firstArg match {
  case "salt" => "pepper"
  case "chips" => "salsa"
  case "eggs" => "bacon"
  case _ => "huh"
}
```

Scala doesn't support `continue` and `break`. To program without these two keywords, replace every continue by an if and break by a boolean variable. For example, supposes you are searching thorugh an argument list for a string that ends with ".scala" but does not start with a hyphen.

```java
int i = 0;
boolean foundIt = false;
while (i <  args.length) {
  if (args[i].startsWith("-")) {
    i = i + 1;
    continue;
  }
  if (args[i].endsWith(".scala")) {
    foundIt = true;
    break;
  }
  i += 1
}
```

```scala
var i = 0
var foundIt = false
while (i <  args.length && !foundIt) {
  if (!args(i).startsWith("-")) {
    if (args(i).endsWith(".scala"))
      foundIt = true
  }
  i += 1
}
```

To get rid of `var`, you could use recursive function.

```scala
def searchFrom(i: Int): Int =
  if (i >= args.length) - 1
  else if (args(i).startsWith("-")) searchFrom(i + 1)
  else if (args(i).endsWith(".scala")) i
  else searchFrom(i + 1)
val i = searchFrom(0)
```

## Closures and functions

```scala
import scala.io.Source

object LongLines {
  def processFile(filename: String, width: Int) = {
    val source = Source.fromFile(filename)
    for(line <- source.getLines())
      processLine(filename, width, line)
  }
  private def processLine(filename: String, width: Int, line: String) = {
    if (line.length > width)
      println(filename + ": " + line.trim)
  }
}
```

Similar to local variables, local functions are visible only in their enclosing block.

```scala
def processFile(filename: String, width: Int) = {
  def processLine(line: String) = {
    if (line.length > width) 
      println(filename + ": " + line.trim)
  }
  val source = Source.fromFile(filename)
  for(line <- source.getLines())
    processLine(line)
}
```

Scala has *first-class functions*. A function literal is compiled into a class that when instantiated at runtime is a function value.
Scala provides a number of ways to leave out redundant information and write function literals more briefly.

```scala
someNumbers.filter(x => x > 0)
```

To make function literal even more concise, you can use underscores for one or more parameters, so long as each parameter appears only one time within the function literal.

```scala
someNumbers.filter(_ > 0)
someNumbers.foreach(println _)
```

A **partially applied function** is an expression in which you don't supply all of the arguments needed by the function.

```scala
val b = sum(1, _: Int, 3)
b(2) // 6
```

You can express a partially applied function by supplying some of the required arguments.

In Scala, you can pass work on variables not defined in the function.

```scala
(x: Int) => x + somethingElse
var somethingElse = 1
val addSomethingElse = (x:Int) => x + somethingElse
addSomethingElse(10) // 11
```

Here, `somethingElse` is called a free variable and not defined in the scope of function. The function value that's created at runtime from this function literal is called a **closure**. The name arises from the act of "closing" the function literal by capturing the bindings of the free variables. If variable `somethingElse` changes after the closure is created, the closure sees the change. The opposite is also true. changes made by a closure to a captured variable `somethingElse` are visible outside the closure

```scala
somethignElse = 9999
addSomethingElse(10) // 10009

// changes visible outside closure
val someNumbers = List(-11, -10, -5, 0, 5, 10)
var sum = 0
someNumbers.foreach(sum += _)
sum // -11
```

Since function calls are central in Scala, a few special forms of function definitions and function calls have been added to the language. Scala allows you to indicate that the last parameter to a function may be repeated. This allows to pass variable length argument lists to the function. For this, put asterisk after the type fo the parameter. Inside the function, the type of repeated parameter is Array of declared type of parameter.

```scala
def echo (args: String*) =
  for (arg <- args) println(arg)
echo()
echo("hello", "world")
```

If you have an array of appropriate type, you attempt to pass it as a repeated parameter, you get a compile error. To accomplish this, you'll need to append the array argument with a colon and an `_*`.

```scala
val arr = Array("What's", "up", "doc?")
echo(arr) // compile error due to type match
echo(arr: _*)
```

**Named arguments** allow you to pass arguments to a function in a different order. It is also possible to mix positional and named arguments. In that case, the positional arguments come first. Named arguments are mostly used with default parameter values. The argument for such a parameter can optionally be omitted from a function call.

```scala
def speed(distance: Float, time: Float): Float =
  distance / time
speed(time = 10, distance = 100)
// Default parameters
def printTime(out: java.io.PrintStream = Console.out) = 
  out.println*("time = " + System.currentTimeMillis())
```

### Tail Recursion

Let's see recursive function that improves result of approximation.

```scala
// Normal recursive function
def approximate(guess: Double): Double =
  if (isGoodEnough(guess)) guess
  else approximate(improve(guess))
```

Functions which call themselves as their last action are called tail recursive. Scala compiler detects tail recursion and replaces it with a jump back to the beginning of the function after updating the function parameters with new values. A tail-recursive function will not build a new stack frame for each call; all calls execute in a single frame.

```scala
def boom(x: Int): Int =
  if (x == 0) throw new Exception("boom")
  else boom(x - 1) + 1
```

Above function is not tail recursive because it performs an increment operation after recursive call.

```scala
def bang(x: Int): Int =
  if (x == 0) throw new Exception("bang")
  else bang(x - 1)
```

Now, when we run `boom`, it gives long stacktrace and when we run `bang` it gives smaller stack trace. You can turn off tail call optimizations using `-g:notailcalls`. Scala only optimizes directly recursive calls back to the same function making the call. If the recursion is indirect, no optimization is possible.
You also won't get a tail-call optimization if the final call goes to a function value.

```scala
// no optimization possible
def isEven(x: Int): Boolean =
  if (x == 0) true else isOdd(x - 1)

val funValue = newstedFun _
def nestedFun(x: Int): Unit = {
  if (x != 0) { println(x); funValue(x - 1)}
}
```

Function values allow for reduction of code duplication. For example, if you want to implement a file traversing and want to implement function that selects only scala source files.

```scala
def filesEnding(query: String) =
  for (file <- files; if file.getName.endsWith(query))
    yield file
```

If you also want to search files with any string and want to allow users to search files based on regex, you can do that easily using  following two snippets.

```scala
if file.getName.contains(query)
file.getName.matches(query)
```

You can pass around function values to achieve this concisely. Following shows the solution for this.

```scala
def filesMatching(query: String, matcher: (String, String) => Boolean) = {
  for (file <- files; if matcher(file.getName, query))
    yield file
}

def filesEnding(query: String) = filesMatching(query, _.endsWith(_))
def filesContaining(query: String) = filesMatching(query, _.contains(_))
def filesRegex(query: String) = filesMatching(query, _.matches(_))
```

This could be even simplied as below `filesMatching` function.

```scala
def filesMatching(matcher: String => Boolean) = 
  for (file <- files; if matcher (file.getName)) yield file

def filesEnding(query: String) + filesMatching(_.endsWith(query))
```

### Currying

A curried function is applied to multiple argument lists, instead of one.

```scala
// non-curried function
def plainOldSum(x: Int, y: Int) = x + y
plainOldSum(1,2)
// curried function, uses two lists of one Int parameters each
def curriedSum(x: Int) (y: Int) = x + y
curriedSum(1)(2)
```

The first function invocation takes a single Int parameter named x, and returns a function value for the second function. It works like this.

```scala
def first(x: Int) = (y: Int) => x + y
val second = first(1) // returns Function1
second(2) // 3
```

You can get reference to curriedSum's second function. You can use the placeholder notation to use curriedSum in a partially applied function expression.

```scala
val onePlus = curriedSum(1)_ // result is reference to a function that adds one to its argument
onePlus(2) // 3
val twoPlus = curriedSum(2)_
twoPlus(2) // 4
```

Any time you find a control pattern repeated in multiple parts of code, you should think about implementing it as a new control structure.

```scala
def withPrintWriter(file: File, op: PrintWriter => Unit) = {
  val writer = new PrintWriter(file)
  try {
    op(writer)
  } finally {
    writer.close()
  }
}

withPrintWriter(new File("date.txt"), writer => writer.println(new java.util.Date))
```

`withPrintWriter` method assures the file is closed at the end. So, it's impossible to forget to close the file. This technique is called **loan pattern** because a control-abstraction function such as `withPrintWriter` opens a resource and loans it to a function.
Because the function passed to `withPrintWriter` is the last argument in the list, you can use currying to pull the first argument into a separate argument list.

```scala
def withPrintWriter(file: File)(op: PrintWriter => Unit) = {
  val writer = new PrintWriter(file)
  try {
    op(writer)
  } finally {
    writer.close()
  }
}
val file = new File("date.txt")
withPrintWriter(file){ writer =>
  writer.println(new java.util.Date)
}
```

To make by-name parameter, you give the parameter a type starting with `=>` instead of `()=>`. You can change predicate parameter of `myAssert` into a by-name parameter by changing its type, `() => Boolean` into `=> Boolean`.

```scala
var assertionsEnabled = true
def myAssert(predicate: () => Boolean) =
  if (assertionsEnabled && !predicate())
    throw new AssertionError
myAssert(() => 5 > 3)
myAssert(5 > 3) // wont' work, because missing () =>

def byNameAssert(predicate: => Boolean) = 
  if (assertionsEnabled && !predicate)
    throw new AssertionError
byNameAssert(5 > 3)
```

A by-name type is only allowed for parameters. There is no such thing as a by-name variable or a by-name field.

## Composition and Inheritance

Composition means one class holds reference to another, using the referenced class to help it fulfill its mission. Inheritance is superclass/subclass relationship.

```scala
abstract class Element {
  def contents: Array[String]
  def height: Int = contents.length
  def width: Int = if (height == 0) 0 else contents(0).length
}
```

In Scala, method is abstract if it does not have implementation. It does not need `abstract` modifier on method declarations. You can also note that if the method does not have any parameters, the method can be defined without empty parentheses.
We can also implement width and height as fields, instead of methods

```scala
abstract class Element {
  def contents: Array[String]
  val height = contents.length
  val width = if (height == 0) 0 else contents(0).length
}
```

The difference is that field accesses might be slightly faster because the field values are pre-computed when the class is initialized, instead of being computed on method call. The fields require extra memory space in each object. So, it depends on usage profile of a class.
In Scala, it's possible to leave out all empty parenthese in function calls.
Implement concrete class of Element

```scala
class ArrayElement(conts: Array[String]) extends Element {
  def contents: Array[String] = conts
}
```

If you don't use `extends`, Scala compiler implicitly assumes your class extends from `scala.AnyRef` similar to Java's `java.lang.Object`. With this all members of superclass are inherited except the private members. Now with concrete implementation, we can instantiate objects.

```scala
val ae = new ArrayElement(Array("hello", "world"))
ae.width // 5
```

You can prefix a class parameter with `var` to make field reassignable.

```scala
class LineElement(s: String) extends ArrayElement(Array(s)) {
  override def width = s.length
  override def height = 1
}
```

To invoke a superclass constructor, you place the argument or arguments you want to pass in parenthese following the name of the superclass.

```scala
class UniformElement(
  ch: Char,
  override val width: Int,
  override val height: Int
) extends Element {
  private val line = ch.toString * width
  def contents = Array.fill(height)(line)
}

val e1: Element = new ArrayElement(Array("hello", "world"))
val ae: ArrayElement = new LineElement("hello")
val e2: Element = ae
val e3: Element = new UniformElement('x', 2, 3)
```

Method invocations on variables and expressions are dynamically bound. The actual method implementation invoked is determined at runtime based on the class of the object, not the type of the variable or expression.

If you want to ensure that a member cannot be overridden by subclasses, you can use `final` modifier to the member. You can also make entire class cannot be subclassed.

```scala
class ArrayElement extends Element {
  final override def demo() = {
    println("ArrayElement's implementation")
  }
}
```

```scala
class LineElement(s: String) extends Element {
  val contents = Array(s)
  override def width = s.length
  override def height = 1
}
```

A factory object contains methods that construct other objects. Such factory object could be created as a companion object of a class Element.

```scala
object Element {
  def elem(contents: Array[String]): Element = new ArrayElement(contents)
  def elem(chr: Char, width: Int, height: Int): Element
    = new UniformElement(chr, width, height)
  def elem(line: String): Element = new LineElement(line)
}
```

In Scala, every class inherits from a common superclass named `Any`. `Nothing` is a subclass of every other class. The root class `Any` has two subclasses: `AnyVal` is parent class of value classes in Scala. The other subclass of the root class is `AnyRef`. This is base class of all reference classes in Scala. Because Nothing is subtype of every other type, we can use methods like below in flexible ways.

```scala
def divide(x: Int, y: Int): Int =
  if (y != 0) x / y
  else throw new RunTimeException("can't divide by zero")
```


To define a value class, we can extend `AnyVal`.

```scala
class Dollars(val amount: Int) extends AnyVal {
  override def toString() = "$" + amount
}
class SwissFrancs(val amount: Int) extends AnyVal {
  override def toString() = amount + " CHF"
}
val money = new Dollars(1000)
money.amount // 1000
val francs = new SwissFrancs(1000)
```

## Traits

traits are useful for reuse in Scala. Unlike class inheritance, in which each class must inherit from just one superclass, a class can mix in any number of traits.

```scala
trait Philosophical {
  def philosophize() = {
    println("I consume memory, therefor I am")
  }
}
```

It does not declare a superclass, so like a class, it has the default superclass of `AnyRef`. Once a trait is defined, it can be mixed in to a class using either the `extends` or `with` keywords. Scala programmers mix in traits rather than inherit from them.

```scala
class Frog extends Philosophical {
  override def toString = "green"
}
```

You can use the `extends` keyword to mix in a trait in which case you implicitly inherit the trait's superclass. Methods inherited from a trait can be used just like methods inherited from a superclass.

```scala
val frog = new Frog
frog.philosophize() // I consume memory, therefor I am
val phil: Philosophical = frog // use Philosophical as a type
phil.philosophize() // I consume memory, therefor I am
```

To mix in a trait into a class that explicitly extends a superclass, use `extends` to indicate the superclass and `with` to mix in the trait. The subclass can also override implementation of methods defined in traits.

```scala
class Animal
trait HasLegs

class Frog extends Animal with Philosophical with HasLegs {
  override def toString = "green"
  override def philosophize() = {
    println("It ain't easy being " + toString)
  }
}

val phrog: Philosophical = new Frog
phrog.philosophize() // It ain't easy being green
```

Traits, unlike Java interfaces, can declare fields and maintain state. A trait cannot have any class parameters (parameters passed to primary constructor of a class).

```scala
class Point(x: Int, y: Int)
trait NoPoint(x: Int, y: Int) // does not compile
```

In classes, super calls are statically bound, in traits, they are dynamically bound. If you write `super.toString` in a class, you know exactly which method implementation will be invoked. When you write the same with trait, the method implementation to invoke for the super class is undefined when you define the trait. The implementation to invoke will be determined each time the trait is mixed into a concrete class.

```scala
class Point(val x: Int, val y: Int)

trait Rectangular {
  def topLeft: Point
  def bottomRight: Point
  def left = topLeft.x
  def right = bottomRight.x
  def width = right - left
}

abstract class Component extends Rectangular {}

class Rectangle(val topLeft: Point, val bottomRight: Point) extends Rectangular {
  // other methods, it gets few method implementations from trait
}

val rect = new Rectangle(new Point(1,1), new Point(10,10))
rect.left // 1
rect.right // 10
rect.width // 9
```

We can define `Ordered` trait that includes `compare` method and then we can use that method to define relational operations for that data type. This trait defines one abstract method, `compare`, which is expected to compare the receiver (this) against another object of the same type.

```scala
trait Ordered[T] {
  def compare(that: T): Int
  def <(that: T): Boolean = (this compare that) < 0
  def >(that: T): Boolean = (this compare that) > 0
  def <=(that: T): Boolean = (this compare that) <= 0
  def >=(that: T): Boolean = (this compare that) >= 0
}
class Rational(n: Int, d: Int) extends Ordered[Rational] {
  def compare(that: Rational) = (this.numer * that.denom) - (that.numer * this.denom)
}
val half = new Rational(1, 2)
val third = new Rational(1, 3)
half < third // false
```

Traits let you modify the methods of a class, and they do so in a way that allows you to stack those modifications with each other. Given a class that implements a queue (FIFO), you could define traits to perform modifications such as these:
- Doubling: double all integers that are put in the queue
- Incrementing: increment all integers that are put in the queue
- Filtering: filter out negative integers from a queue

```scala
abstract class IntQueue {
  def get(): Int
  def put(x: Int)
}

import scala.collection.mutable.ArrayBuffer

class BasicIntQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]
  def get() = buf.remove(0)
  val put(x: Int) = { buf += x }
}

// Doubling trait declares superclass, IntQueue means that trait can only be mixed into a 
// class that also extends IntQueue
// Thus, Doubling can be mixed into BasicIntQueue but not Rational object.
trait Doubling extends IntQueue {
  // super call in trait will work so long as the trait is mixed in after another trait or class that 
  // gives concrete definitions to the method.
  abstract override def put(x: Int) = { super.put(2 * x)}
}

class MyQueue extends BasicIntQueue with Doubling
val queue = new MyQueue
queue.put(10)
queue.get() // 20, due to Doubling trait
// We can also do following as MyQueue doesn't define any new code.
val queue = new BasicIntQueue with Doubling
queue.put(10)
queue.get() // 20

trait Incrementing extends IntQueue {
  abstract override def put(x: Int) = { super.put(x + 1) }
}
trait Filtering extends IntQueue {
  abstract override def put(x: Int) = {
    if (x >= 0) super.put(x)
  }
}
```

With new modifications of `Incrementing` and `Filtering` defined, you can pick and choose which ones you want for a particular queue. The order of mixins is important as well. Traits further to the right take effect first. For example, in following code `Filtering`'s put method is invoked first and then `Incrementing`'s put method.

```scala
val queue = (new BasicIntQueue with Incrementing with Filtering)
queue.put(-1); queue.put(0); queue.put(1);
queue.get() // 1
queue.get() // 2
```

If the order is changed, -1 will not be filtered from the queue.

```scala
val queue = (new BasicIntQueue with Filtering with Incrementing)
queue.put(-1); queue.put(0); queue.put(1)
queue.get() // 0
queue.get() // 1
queue.get() // 2
```

In Scala, linearization helps find the precise order of method calls in inheritance hierarchy. In any linearization, a class is always linearized in front of all its superclasses and mixed in traits.

```scala
class Animal
trait Furry extends Animal
trait HasLegs extends Animal
trait FourLegged extends Animal
class Cat extends Animal with Furry with FourLegged
```

Method calls from `Cat` class are resolved in following order. Cat, FourLegged, HasLegs, Furry, Animal, AnyRef, Any.
Here are some rules whether you should use traits or abstract class.

1. If the behaviour will not be reused, then make it a concrete class. It is not reusable after all.
2. If it might be reused in multiple, unrelated classes, make it a trait. Only traits can be mixed into different parts of class hierarchy.
3. If you want to inherit from it in Java code, use an abstract class. Since traits with code do not have a close Java analog, it tends to be awkward to inherit from a trait in a Java class.
4. If you plan to distribute it in compiled form, and you expect outside groups to write classes inheriting from it, you might lean towards abstract class.
5. If unknown, starts by making it as a trait.

## Packages and Imports

Scala allows defining packages like Java. The other way you can place code into packages is more like C# namespaces. This syntax is called a *packaging*.

```scala
package bobsrockets.navigation {
  class Navigator
}
```

Similarly, you can use nested package structure. You can put the class's tests in the same file as the original code, but in different package.

```scala
package bobsrockets {
  package navigation {
    class Navigator
    package tests {
      class NavigatorSuite
    }
  }
}
```

```scala
package bobsrockets {
  package navigation {
    class Navigator {
      val map = new StarMap
    }
    class StarMap
  }
  class Ship {
    // No need to say bobsrockets.navigation.Navigator
    val nav = new navigation.Navigator
  }
  package fleets {
    class Fleet {
      // No need to say bobsrockets.Ship
      def addShip() = { new Ship }
    }
  }
}
```

A class can be accessed from within its own package without needing a prefix. A package itself can be accessed from its containing package without needing a prefix. When using the curly-braces packaging syntax, all names accessible in scopes outisde the packaging are also available inside it. Scala provides package named `_root_` that is outside any package a user can write. To access `Booster3`, we can use `_root_.launch.Booster3` to access that class.

```scala
package launch {
  class Booster3
}
package bobsrockets {
  package navigation {
    package launch {
      class Booster1
    }
    class MissionControl {
      val booster1 = new launch.Booster1
      val booster2 = new bobsrockets.launch.Booster2
      val booster3 = new _root_.launch.Booster3
    }
  }
  package launch {
    class Booster2
  }
}
```

Packages and their members can be imported using **import clauses**.

```scala
pckage bobsdelights

abstract class Fruit (val name: String, val color: String)

object Fruits {
  object Apple extends Fruit("apple", "red")
  object Orange extends Fruit("orange", "orange")
  object Pear extends Fruit("pear", "yellowish")
  val menu = List(Apple, Orange, Pear)
}
```

In other file, you can access them using following.

```scala
import bobsdelights.Fruit // access fruit object
import bobsdelights._ // import all members of bobsdelights package
import bobsdelights.Fruits._ // all members of fruits
```

In Scala, imports may appear anywhere, may refer to objects in addition to packages, imports let you rename and hide some of the imported members.

```scala
import Fruits.{Apple, Orange} // import only Apple and Orange from object Fruits
import Fruits.{Apple => McIntosh, Orange} // Apple is renamed to McIntosh
import Fruits.{Pear => _, _} // import all members of Fruits except Pear
```

A clause of form `<original_name> => _` excludes `<original_name>` from the names that are imported.
Scala adds following imports implicitly to every program.

```scala
import java.lang._
import scala._
import PreDef._
```

The `PreDef` object contains many definitions of types, methods and implicit conversions that are commonly used on Scala programs. These three import clauses are treated a bit specially in that later imports overshadow earlier ones. For example, `StringBuilder` class is defined in package `scala` and in `java.lang`. `scala`'s import overshadows the `java.lang` import.

### Access modifiers

Scala's access modifiers roughtly follows Java's rules. A member labeled private is visible only inside the class or object that contains the member definition.

```scala
class Outer {
  class Inner {
    private def f() = { println("f") }
    class InnerMost {
      f() // ok
    }
  }
  (new Inner).f() // error: f is not accessible
}
```

In Scala, the access `(new Inner).f()` is illegal because `f` is declared private in Inner and the access is not from within class Inner.
In Scala, a `protected` member is only accessible from subclasses of the class in which the member is defined. In Java, such accesses are also possible from other classes in the same package.

```scala
package p {
  class Super {
    protected def f() = { println("f") }
  }
  class Sub extends Super {
    f() // ok
  }
  class Other {
    (new Super).f() // error: f is not accessible
  }
}
```

In Java, the Other class would be permitted to access `f()` method as it is in the same package as Sub.
Scala has no explicit modifier for public members; Any member not labeled `private` or `protected` is public.

```scala
package bobsrockets

package navigation {
  private[bobsrockets] class Navigator {
    protected[navigation] def useStarChart() = {}
    class LegOfJourney {
      private[Navigator] val distance = 100
    }
    private[this] var speed = 200
  }
}
package launch {
  import navigation._
  object Vehicle {
    private[launch] val guide = new Navigator
  }
}
```

A modifier of the form `private[X]` or `protected[X]` means that access is private or protected up to X. Such access modifiers give you very fine-grained control over visibility. Scala has access modifier `private[this]` which is accessible only from within the same object that contains the definition.

```scala
val other = new Navigator
other.speed // this does not compile
```

In Scala, there are no static members; instead you can have a companion object that cnotains members that exist only once. Scala's access rules privilege companion objets and classes when it comes to private or protevted accesses. A class shares all its access rights with its companion object and vice versa. An object can access all private members of its companion class.

```scala
class Rocket {
  import Rocket.fuel
  private def canGoHomeagain = fuel > 20
}
object Rocket {
  private def fuel = 10
  def chooseStrategy(rocket: Rocket) = {
    if (rocket.canGoHomeAgain)
      goHome()
    else
      pickAStar()
  }
  def goHome() = {}
  def pickAStar() = {}
}
```

Each package is allowed to have one **package object**. Any definitions placed in a package object are considered members of the package itself. If you have some helper method you'd like to be in scope for an entire package, put it at the top level of the package.

```scala
// bobsdelights/package.scala file
package object bobsdelights {
  def showFruit(fruit: Fruit) = {
    import fruit._
    println(name + "s are " + color)
  }
}
package printmenu
import bobsdelights.Fruits
import bobsdelights.showFruit
object PrintMenu {
  def main(args: Array[String]) = {
    for (fruit <- Fruits.menu) {
      showFruit(fruit)
    }
  }
}
```

Package objects are used to hold package-wide type aliases and implicit conversions. Package objects are compiled to class files named `package.class` that are located in directory of the package that they augment.

## Case Classes

```scala
abstract class Expr
case class Var(name: String) extends Expr
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(opeartor: String, left: Expr, right: Expr) extends Expr
```

The bodies of all five classes are empty. Classes with `case` modifier are called *case classes*. It adds a factory method with the name of the class. This means you can write `Var("x")` to construct a `Var` object instead of `new Var("x")`. Second, all arguments in the parameter list of a case class implicitly get a `val` prefix, so they are maintained as fields. The compiler also adds natural implementations of methods `toString`, `hashCode` and `equals` to your class. The compiler also adds a `copy` method to your class for making modified copies. This method is useful for making a new instance of the class that is the same as another one except that one or two attributes are different. You specify the changes you'd like to make by using named parameters.

```scala
val v = Var("x") // factory methods
val op = BinOp("+", Number(1), v)
v.name // get methods
op.left
println(op) // toString
op.copy(operator = "-")
```

With case classes your objects become a bit larger because additional methods are generated and an implicit field is added for each constructor parameter. These case classes support pattern matching.

```scala
def simplifyTop(expr: Expr): Expr = expr match {
  case UnOp("-", UnOp("-", e)) => e
  case BinOp("+", e, Number(0)) => e
  case BinOp("*", e, Number(1)) => e
  case _ => expr
}
simplifyTop(UnOp("-", UnOp("-", Var("x"))))
```

A **pattern match** includes a sequence of alternatives, each starting with the keyword case. A match expression is evaluated by trying each of the patterns in the order they are written. The first pattern that matches is selected, and the part following the arrow is selected and executed. The wildcard pattern (_) matches every value

Match expression can be compared with Java switch statement. There are few differences. Match is an expression and always results in a value. Match never fall through into the next case. If none of the patterns match, an exception named `MatchError` is thrown. This means you have to make sure that all cases are covered.

With Pattern matching, we can use different kinds of patterns:
- wild card pattern that matches everything
- constant pattern that matches literal or any constant value like 5, true, "hello"
- Constructor patterns
- Sequence patterns, you can also match against the elements of the sequence.
- Tuple pattern
- Typed patterns

```scala
x match {
  case 5 => "five"
  case Nil => "empty list" // constant
  case _ => "something else" // wild card
}
expr match {
  case BinOp("+", e, Number(0)) => println("a deep match") // constructor pattern
  case _ =>
}
expr match {
  case List(0, _, _) => println("found it") // sequence pattern with 3 elements and first element zero
  case List(0, _*) => println("list with first 0 and any number of elements")
  case (a, b, c) => println("tuple with 3 elements")
}
// Typed pattern
x match {
  case s: String => s.length
  case m: Map[_, _] => m.size
  case _ => -1
}
```

To test whether an expression `expr` has type String, you write `expr.isInstanceOf[String]`. To cast to type String, you use `expr.asInstanceOf[String]`. These two methods are pre-defined in class `Any` It is better to use pattern match with typed pattern.

Now, we can check whether particular object is a `Map` of some data type key and other datatype value. However, due to Java style type-erasure, we cannot check whether Map is a `Map[Int, Int]`. At runtime, Scala compiler doesn't know the type of typed parameters. The only exception to type erasure is arrays because they are handled specially in Java and Scala.

```scala
def isIntIntMap(x: Any) = x match {
  case m: Map[Int, Int] => true
  case _ => false
}
isIntIntMap(Map(1 -> 1)) // true
isIntIntMap(Map("abc" -> "abc")) // true

def isStringArray(x: Any) = x match {
  case a: Array[String] => "yes"
  case _ => "no"
}
val as = Array("abc")
val ai = Array(1,2,3)
isStringArray(ai) // no
```

Id you are given a task of formulating a simplification rule that replaces sum expressions with two identical operands such as `e + e` by multiplication of two `e * 2`, you can try like below.

```scala
def simplifyAdd(e: Expr) = e match {
  case BinOp("+", x , x) => BinOp("*", x, Number(2)) // fails
  case _ => e
}
```

This fails because Scala restricts patterns to be linear: a pattern variable may only appear once in a pattern. However, **pattern guards** can help with this. If pattern guard is present, the match succeeds only if guard evaluates to true.

```scala
def simplifyAdd(e: Expr) = e match {
  case BinOp("+", x, y) if x == y => BinOp("*", x, Number(2)) // ok
  case _ => e
}
```

Whenver you write a pattern match, you need to make sure you have covered all cases. You can enlist the help of Scala compiler in detecting missing combinations of patterns in a match expression. This is not possible as case classes can be defined anywhere and a new Expr class can be created. A **sealed class** cannot have any new subclases added except the ones in the same file. If you write hierarchy of classes to be pattern matched, you should consider sealing them.

``scala
sealed abstract class Expr
case class Var(name: String) extends Expr
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr
```

Now define pattern match where some possible cases are left out.

```scala
def describe(e: Expr): String = e match {
  case Number(_) => "a number"
  case Var(_) => "a variable"
}
```

When compiling, you will get warning message "match is not exhaustive!" which tells you that there is a possibility of `MatchError`. Another alternative is to add `@unchecked` annotation to the selector expression of the match to avoid not defining all possible cases.

```scala
def describe(e: Expr): String = (e: @unchecked) match {
  case Number(_) => "a number"
  case Var(_) => "a variable"
}
```

Scala has standard type named `Option` for optional values. Such a vaue can be either `Some(x)` or `None` which represents missing value. 

```scala
var capitals = Map("France" -> "Paris", "Japan" -> "Tokyo")
capitals get "France" // Some(Paris)
capitals get "North Pole" // None

def show(x: Option[String]) = x match {
  case Some(s) => s
  case None => "?"
}
show (capitals get "Japan") // Tokyo
show(capitals get "North Pole") // ?
```

This Option types are good for programs where in Java you had to check for the value to be null using if statements. Scala encourages the use of Option to indicate an optional value. 
You can use pattern matching to deconstruct a case class or a tuple.

```scala
val exp = new BinOp("*", Number(5), Number(1))
val BinOp(op, left, right) = exp // op: *, left: Number(5.0), right: Number(1.0)
```

A sequence of cases gives you a **partial function**. If you apply such a function on a value it does not support, it will generate a run-time exception.

```scala
val second: List[Int] => Int = {
  case x :: y :: _ => y
}
```

Above function will succeed if you pass it a three-element list, but not if you pass it an empty list. If you want to chcek whether a partial function is defined, you can use `isDefinedAt` method.

```scala
second(List(5,6,7)) // 6
second(List()) // MatchError
second.isDefinedAt(List(5,6,7)) // true
```

In general, you should try to work with complete functions whenever possible, because partial functions allow for runtime errors that compiler cannot help you with.
We can also use pattern in a for expression.

```scala
for ((country, city) <- capitals)
  println("The capital of " + country + " is " + city)
```

```scala
val results = List(Some("apple"), None, Some("orange"))
for (Some(fruit) <-  results) println(fruit) // apple, orange
```

