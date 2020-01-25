# Functional Programming

Functional programming is based on the rules of mathematics for the behavior of functions and values. In mathematics, functions have no side effects. Such functions are free of *side effects* or *pure*. This means concurrent invocation of the function is straightforward.

A function that returns Unit can only perform side effects. It can only modify mutable state somewhere. Functions are first-class values in functional programming. You can compose functions from another functions. You can assign functions to variables and pass functions as arguments and return functions as values from functions. When a function takes other function as arguments or returns a function, it is called **higher-order function**. Values are also immutable. Even during assignment, you create new variable instead of changing earlier one. Immutability has many benefits for writing code that scales through concurrency. Most of the difficulty of multithreaded programming lies in synchronizing access to shared, mutable state. The problem with immutability is that performance can be slow. Howver, functional data structures minimiz the overhead of making copies by sharing the unmodified parts of the data structures between two copies. Higher-order, pure functions are called *combinators*. Another source of performance gains is the use of data structures with lazy evaluation such as Stream type. Functional programming can iterate through values.

```scala
(1 to 10) filter (_ % 2 == 0) map (_ * 2) reduce (_ * _)
```

The operation passed to `reduce` function must be associative.

[Closure example](../src/main/scala/functional/hofs-closure-example.sc)

[Closure example 2](../src/main/scala/functional/hofs-closure-example2.sc)

We cam have multiplier as method inside an object. When a method is used where a function is required, we say that Scala lifts the method to be a function.

[Example 3 Functions as methods](../src/main/scala/functional/function-as-method.sc)

**Function** is an operation that is named or anonymous and its code is not evaluated until it is called. **Lambda** is an anonymous function. It may or may not have free variables in its definition. **Closure** is a function, anonymous or named, that closes over its environment to bind variables in scope to free variables within the function.

## Recursion

Recursion is the pure way to implement looping because you can't have mutable loop counters.

Java implementation of recursion
```java
public class Factorial {
    public static long factorial(long l) {
        long result = 1L;
        for(long j = 2L; j <= l; j++) {
            result *= j;
        }
        return result;
    }
    public static void main(String args[]) {
        for(long l = 1L; l <= 10; l++) 
            System.out.printf("%d:\t%d\n", l, factorial(l));
    }
}
```

Here's Scala implementation

```scala
import scala.annotation.tailrec

// @tailrec
def factorial(i: BigInt): BigInt = 
    if(i == 1) i
    else i * factorial(i - 1)
}
for(i <- 1 to 10)
    println(s"$i:\t${factorial(i)}")

```

However, with recursion there is risk of performance overhead of repeated function calls and the risk of stack overflow. It would be nice if we could write pure functional, recursive function and compiler would optimize it into a loop.

### Tail calls and tail-call optimization

Tail call occurs when a function calls itself and the call is the final operation it does. Tail call is the easiest kind of recursion to optimize by conversion into a loop. Loops elimintate the potential of a stack overflow and improve performance by elimintating recursive function calls. The above *factorial* function is not tail recursino because it calls itself and then does the multiplication with the result. If you have `@tailrec` and compiler can't optimize those recursive calls, it will throw an exception.

Here, is the updated version with tailrec.

```scala
import scala.annotation.tailrec

def factorial(i: BigInt): BigInt = {
  @tailrec
  def fact(i: BigInt, accumulator: BigInt): BigInt = {
    if(i == 1) accumulator
    else fact(i - 1, i * accumulator)
  }
  fact(i, 1)
}
for (i <- 1 to 1o)
    println(s"$i:\t%{factorial(i)}")

```

Now, fact is tail recursive because it passes an accumulator argument to hold the computation in progress. The tail-call optimization won't be applied when a method that calls itself might be overridden in a derived type. Hence, the recursive method must be defined with the *private* or *final* keyword, or it must be nested in another method.

A *trampoline* is a loop that works through a list of functions, calling each one in turn. A kind of recursion where a function A doesn't call itself recursively, but calls another function B, which then calls A, etc. This kind of back-and-forth recursion can also be converted into a loop using a trampoline, a data structure that makes it easier to perform such calculations without recursive function calls.

In efficient way to dtermine is number is even or odd

```scala
import scala.util.control.TailCalls._

def isEven(xs: List[Int]): TailRec[Boolean] = 
    if (xs.isEmpty) done(true) else tailcall(isOdd(xs.tail))
    
def isOdd(xs: List[Int]): TailRec[Boolean] =
    if(xs.isEmpty) done(false) else tailcall(isEven(xs.tail))

for(i <- 1 to 5) {
  val even = isEven((1 to i).toList).result
  println(s"$i is even? $even")
}

```

This code bounces back and forth for each list element until the end of the list.

### Partially applied vs Partial functions

```scala
def cat1(s1: String)(s2: String) = s1 + s2
// Define a function that will have hello at the beginning
val hello = cat1("Hello ")_
hello("World!")
cat1("Hello ")("World!")
val hello = cat1("Hello ") // no underscore after first argument gives error

```

For a function with more than one argument list, you can define a new function if you omit one or more of the trailing arguments.
A **partially applied function** is an expression with some, but not all of a function's argument list applied (provided), returning a new function that takes the remaining argument lists. A **partial function** is a single-argument function that is not defined for all values of the type of its argument.
 
 ### Currying
 
 Currying transforms a function that takes multiple arguments into a chain functions, each taking a single argument. In Scala, curried functions are defined with multiple argument lists, each with a single argument.
 
 ```scala
 def cat1(s1: String)(s2: String) = s1 + s2
 def cat2(s1: String) = (s2: String) => s1 + s2 // cat2 returns a single argument function
 val cat2hello = cat2("Hello ") // returns a single argument function
 cat2hello("World!") // Hello World!
 cat1("foo")("bar") // foobar
 cat2("foo")("bar") // foobar, same result as above
 ```
 
 We can also convert methods that take multiple arguments into a curried form using `curried` method.
 
 
 ```scala
 def cat3(s1: String, s2: String) = s1 + s2
 cat3("hello", "world") // helloworld
 val cat3Curried = (cat3 _).curried
 cat3curried("Hello")("world") // Helloworld
 val f1: String => String => String = (s1: String) => (s2: String) => s1 + s2
 val f2: String => (String => String) = (s1: String) => (s2: String) = s1 + s2
 f1("hello")("world") // helloworld
 f2("hello")("world") // helloworld
 ```
 
 The curried function can be uncurried using `uncurried` method.
 
 ```scala
 val cal3Uncurried = Function.uncurried(cat3Curried)
 cat3Uncurried("hello", "world") // helloworld
 val ff1 = Function.uncurried(f1)
 ff1("hello", "world") // helloworld
 ```
 
 A practical use for currying is to specialize functions for particular types of data.
 
 
 ```scala
 def multiplier(i: Int)(factor: Int) = i * factor
 val byFive = multiplier(5)_
 val byTen = multiplier(10) _
 byFive(2) // 10
 byTen(2) // 20
 
 // call method with three arguments using three element tuple
 def mult(d1: Double, d2: Double, d3: Double) = d1 * d2 * d3
 val d3 = (2.2, 3.3, 4.4)
 mult(d3._1, d3._2, d3._3)
 // OR, better
 val multTupled = Function.tupled(mult _)
 multTupled(d3) // same output but different syntax
 val multUnTupled = Function.untupled(multTupled) // revert back
 ```
 
 We can use transformations between partial functions and functions that return options.
 
```scala
val finicky: PartialFunction[String, String] = {
  case "finicky" => "FINICKY"
}
finicky("finicky") // FINICKY
finicky("other") // MatchError
val finickyOption = finicky.lift // now it returns Option
finickyOption("finicky") // Some(FINICKY)
finickyOption("other") // None
val finicky2 = Function.unlift("finickyOption") // now similar to finicky function
```
 
If we have a partial function and we don't like the idea of an exception being thrown, we can lift the function into one that returns an Option instead.

### Functional Data Structures

Functional programs put greater emphasis onthe use of a core set of data structures and algorithms, compared to object languages. The proliferation of ad hoc classes undermines the code reuse. Functional programs tend to more concise and achieve better code reuse compared to object programs. The core set typically includes collections like lists, vectors and arrays, maps and sets. Each collection supports side-effect free functions, called *combinators* such as map, filter, fold, etc.

Sequential data structures are traversable