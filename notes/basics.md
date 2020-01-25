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