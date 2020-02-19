# Collections in Scala

## Lists

```scala
val fruit = List("apples", "oranges", "pears")
val nums = List(1,2,3,4,5)
```

Lists are immutable. The elements of a list cannot be changed by assignment. Lists are recursive structure whereas arrays are flat. Lists are homogeneous; the elements of a list all have the same data type. The list is covariant. If S is a subtype of T, then `List[S]` is a subtype of `List[T]`. Empty list has type `List[Nothing]`.

```scala
val nums = 1 :: 2 :: 3 :: 4 :: Nil
val nums = List(1,2,3,4)
// head and tail methods are defined for non-empty lists.
Nil.head // NoSuchElementException
```

List patterns correspond one-by-one to list expressions.

```scala
val List(a,b,c) = fruit // a: apples, b: oranges, c: pears
val a :: b :: rest = fruit // a: apples, b: oranges, rest: List(pears)
```

The cons (::) is a special case of infix operation pattern. As a pattern, an infix operation such as `p op q` is equivalent to `op(p,q)`. A cons pattern `x :: xs` is treated as `::(x, xs)`.
A method is first-order if it does not take any functions as arguments.

```scala
List(1,2) ::: List(3,4,5) // List(1,2,3,4,5)
```

The following is a simple implementation of `append` method of list.

```scala
def append[T](xs: List[T], ys: List[T]): List[T] =
  xs match {
    case List() => ys
    case x :: xs1 => x :: append(xs1, ys)
  }
```

```scala
List(1,2,3).length // 3
val abcde = List('a', 'b', 'c', 'd', 'e')
abcde.last  // e
abcde.init // List(a,b,c,d)
List().init // UnSupportedOperationException: Nil.init
List().last // NoSuchElementException
abcde.reverse // creates new list with order of elements reversed
xs.reverse.reverse equals xs
xs.reverse.init equals xs.tail.reverse
xs.reverse.last equals xs.head
abcde take 2 // List(a,b)
abcde drop 2 // List(c,d,e)
abcde splitAt 2 // (List(a,b), List(c,d,e))
abcde apply 2 // c
abcde(2) // c
abcde.indices // Range(0,1,2,3,4)
List(List(1,2), List(3), List(), List(4,5)).flatten // List(1,2,3,4,5)
fruit.map(_.toCharArray).flatten // List(a,p,p,l,e,s,o,r,a,n,g,e,s,p,e,a,r,s)
// flatten can be applied only to lists whose elements are all lists. Trying to flatten any other list will give compilation error
List(1,2,3).flatten // compilation error
abcde.indices zip abcde // Vector((0,a), (1,b), (2,c), (3,d), (4,e))
// zipping two lists of different lengths
val zipped = abcde zip List(1,2,3) // List((a, 1), (b,2), (c,3))
abcde.zipWithIndex // List((a,0), (b,1), (c,2), (d,3), (e,4))
zipped.unzip // (List(a,b,c), List(1,2,3))
abcde mkString ("[", ",", "]") // [a,b,c,d,e]
abcde.mkString // abcde
abcde mkString("List(", ", ", ")") // List(a, b, c, d, e)
val arr = abcde.toArray // Array(a,b,c,d,e) convert to array
arr.toList // List(a,b,c,d,e) convert back to List
```

Reverse could be implemented using concatenation.

```scala
def rev[T](xs: List[T]): List[T] = xs match {
  case List() => xs
  case x :: xs1 => rev(xs1) ::: List(x)
}
```

There's also a method `copyToArray` which copies list elements to successive array positions within some destination array. `xs copyToArray (arr, start)` copies all elements of the list xs to the array arr, beginning with position `start`.

```scala
val arr2 = new Array[Int](10)
List(1,2,3) copyToArray(arr2, 3) // Array(0,0,0,1,2,3,0,0,0,0)
val it = abcde.iterator // returns non-empty iterator
it.next // a
it.next // b
```

You can implement merge sort like this.

```scala
def msort[T](less: (T, T) => Boolean)(xs: List[T]): List[T] = {
  def merge(xs: List[T], ys: List[T]): List[T] =
    (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xs1, y :: ys1) =>
        if (less(x, y)) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
    }
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (ys, zs) = xs splitAt n
      merge(msort(less)(ys), msort(less)(zs))
    }
  }
}
```

In merge sort, first if the list has zero or one elements, it is already sorted. Longer lists can be split into two sub-lists, each containing about half elements of original. Each sublist is sorted by a recursive call to sort function and the resulting two sorted lists are then combined in a merge operation. The complexity of  msort is `n log(n)`.

```scala
msort((x: Int, y: Int) => x < y)(List(5,7,1,3))
val intSort = msort((x: Int, y: Int) => (x < y)) _
val reverseIntSort = msort((x: Int, y: Int) => x > y)_
mixedInts = List (4, 5,3, 1)
intSort(mixedInts)
reverseIntSort(mixedInts)
```

Class `List` includes methods for transforming every element of a list in some way, verifying whether a property holds for all elements of a list, extracting from a list elements satisfying a criterion, combining elements of a list using some operator.
There is `partition` method which returns pair of lists. One list contains all elements for which the predicate is true and another contains elements for which the predicate is false. The `find` method similar to filter, but return first element matching the predicate condition. The `span` method combines takeWhile and dropWhile in one operation, just like splitAt combines take and drop. `forall` and `exists` method checks all elements and return boolean result.

```scala
List(1,2,3) map (_ + 1) // List(2,3,4)
val words = List("the", "quick", "brown", "fox")
words map (_.length) // List(3, 5, 5, 3)
words map (_.toList.reverse.mkString) // List(eht, kciuq, nworb, xof)
words map (_.toList) // List(List(t,h,e), List(q,u,i,c,k)....)
words flatMap (_.toList) // List(t,h,e,q,u,i,c,k,....)

var sum = 0
List(1,2,3,4,5) foreach ( sum += _ ) // 15
List(1,2,3,4,5) filter (_ % 2 == 0) // List(2,4)
words filter (_.length == 3) // List(the, fox)
List(1,2,3,4,5) partition (_ % 2 == 0) // (List(2,4), List(1,3,5))
List(1,2,3,4,5) find (_ % 2 == 0) // Some(2)
List(1,2,3,4,5) find (_ <= 0) // None
List(1,2,3,-4,5) takeWhile (_ > 0) // List(1,2,3)
words dropWhile (_ startsWith "t") // List(quick, brown, fox)
List(1,2,3,-4,5) span (_ > 0) // (List(1,2,3), List(-4,5))
List (1,2,3,4,5) forall (_ > 0) // true
List(1,2,3,4,5) exists (_ < 0) // false
sum(List(1,2,3)) // 6
product(List(1,2,3)) // 6, 1 * 2 * 3
```

The sum and product methods are special instance of fold operation. A fold operation involves three objects: a start value z, a list xs and a binary operation op. The result of the fold is op applied between successive elements of the list prefixed by z. `(z /: List(a,b,c))(op)` equals `op(op(op(z,a),b),c) which is fold left.

```scala
def sum(xs: List[Int]): Int = (0 /: xs)(_ + _)
// sum(List(a,b,c)) equals 0 + a + b + c
def product(xs: List[Int]): Int = (1 /: xs)(_ * _)
// product(List(a,b,c)) equals 1 * a * b * c
// concatenate all words in a list of string
("" /: words)(_ + " " + _) // " the quick brown fox" -> creates extra white space at the beginning
(words.head /: words.tail)(_ + " " + _) // "the quick brown fox"
```

The operator `/:` produces left-leaning operation tree. The operator `:\` produces right-leaning tree. `(List(a,b,c) :\ z)(op)` equals `op(a, op(b, op(c, z)))`. This is fold right. For associative operations, foldLeft and foldRight are equivalent. There are methods `foldLeft` and `foldRight` which correspond to above methods.

```scala
List(1,-2,4,2,5) sortWith(_ < _) // List(-3,1,2,4,5)
words sortWith (_.length > _.length) // list(quick, brown, the, fox)
List.range(1,5) // list(1,2,3,4)
List.range(1,9, 2) // List(1,3,5,7)
List.range(9,1,-3) // List(9,6,3)
List.fill(5)('a') // List(a,a,a,a,a)
List.fill(2,3)('b') // List(List(b,b,b), List(b,b,b)) multi-dimensional list
// tabulate method creates a list whose elements are computed according to a function.
val squares = List.tabulate(5)(n => n*n) // List(0,1,4,9,16)
// concat method concatenates a number of element lists.
List.concat(List('a','b'), List('c')) // List(a,b,c)
List.concat(List(), List('b'), List('c')) // List(b,c)
(List(10,20), List(3,4,5)).zipped.map(_ * _) // List(30,80)
(List("abc","de"), List(3,2)).zipped.forall(_.length == _) // true
msort[Char](_ > _)(abcde) // List(e,d,c,b,a)
```

## Sequences

Sequences are groups of data lined up in order. **Arrays** allow to hold a sequence of elements and efficiently access an element at an arbitrary position.

```scala
val fiveInts = new Array[Int](5)
val fiveToOne = Array(5,4,3,2,1)
fiveInts(0) = fiveToOne(4) // update an element
```

Class List provides fast access to head, but not the end. A ListBuffer is a mutable object which can help you build lists efficiently when you need to append. It provides constant time access for append and prepend operations.  You append using `+=` and prepend using `+=:` operator. **ListBuffer** also prevents the potential for stack overflow. 

```scala
import scala.collection.mutable.ListBuffer
val buf = new ListBuffer[Int]
buf += 1
buf += 2
buf // ListBuffer(1,2)
3 +=: buf // ListBuffer(3,1,2)
buf.toList // List(3,1,2)
```

**ArrayBuffer** is like an array, except that you can additionally add and remove elements from the beginning and end of the sequence.

```scala
import scala.collection.mutable.ArrayBuffer
val buf = new ArrayBuffer[Int]() // you don't need to specify a length
buf += 12
buf += 15
buf // ArrayBuffer(12, 15)
buf.length // 2
buf(0) // 12
```

One of the sequences is **StringOps**. `Predef` implicitly converts String to StringOps, you can treat any string like a sequence. Because method named `exists` is not declared in String, Scala compiler will implicitly convert s to StringOps.

```scala
def hasUpperCase(s: String) = s.exists(_.isUpper)
```

Scala collections library offers both mutable and immutable versions of sets and maps. By default, when you write "Set" or "Map", you get immutable object. If you want mutable variant, you need to explicitly import it. This easy access is provided by Predef object which is implicitly imported into every Scala source file. The `type` keyword is used in Predef to define Set and Map as aliases for the longer fully qualified names of the immutable set and map traits.

```scala
object Predef {
  type Map[A, +B] = collection.immutable.Map[A, B]
  type Set[A] = collection.immutable.Set[A]
  ... ....
}
```

**Sets** will ensure that there are no duplicate elements

```scala
val text = "See Spot run. Run, spot. Run!"
val wordsArray = text.split("[ !,.]+") // Array(See, Spot, run, Run, Spot, Run)
val words = mutable.Set.empty[String]
for (word <- wordsArray)
  words += word.toLowerCase
words // Set(see, run, spot)
val nums = Set(1,2,3)
nums + 5 // Set(1,2,3,5)
nums - 3 // Set(1,2)
nums ++ List(5,6) /// Set(1,2,3,5,6)
nums - List(1,2) // Set(3)
nums & Set(1,3,5,7) // Set(1,3)
nums.size // 3
nums.contains(3) // true
nums.clear // Set()
import scala.collection.mutable
val words = mutable.Set.empty[String] // create mutable set
words += "the" // Set(the)
words -= "the" // Set()
words ++= List("do", "re", "mo") // Set(do, re, mo)
words --= List("do", "re") // Set(mo)
words.clear // Set()
```

**Maps** let you associate a value with each element of a set. In maps, you use key for indexing an element. With map, you must specify two types when creating a map.

```scala
val map = mutable.Map.empty[String, Int]
map("hello") = 1
map("there") = 2
map // Map(hello -> 1, there -> 2)
map("hello") // 1
def countWords(text: String) = {
  val counts = mutable.Map.empty[String, Int]
  for (rawWord <- text.split("[ ,!.]+")) {
    val word = rawWord.toLowerCase
    val oldCount = 
      if (counts.contains(word)) counts(word)
      else 0
    counts += (word <- (oldCount + 1))
  }
  counts
}
countWords("See spot run! Run, Spot. Run!") // Map(spot -> 2, see -> 1, run -> 3)
val nums = Map("i" -> 1, "ii" -> 2) 
nums + ("vi" -> 6) // Map(i -> 1, ii -> 2, vi -> 6)
nums - "ii" // Map(i -> 1)
nums ++ List("iii" -> 3, "v" -> 5) // Map(i -> 1, ii -> 2, iii -> 3, v -> 5)
nums -- List("i", "ii") // Map()
nums.size // 2
nums.contains("ii") // true
nums("ii") // 2
nums.keys // returns iterable of String "i" and "ii"
nums.keySet // Set(i, ii)
nums.values // returns iterable of 1 and 2
nums.isEmpty // false
import scala.collection.mutable
val words = mutable.Map.emtpy[String, Int]
words += ("one" -> 1)
words -= "one" // words.toString returns Map()
words ++ List("one" -> 1, "two" -> 2, "three" -> 3) // returns map with three elements
words -= List("one", "two") // Map(three -> 3)
```

The `scala.collection.mutable.Set()` factory method returns a `HashSet` which uses hash table internally. Similarly `mutable.Map` return `HashMap`. Both will return a different class depending on how many elements we pass to factory method for permance. For less 5 or more elements, it returns hash table version.
Sometimes yo umay need a set or map whose iterator returns elements in particular order. For this, Scala collections library provides SortedSet and SortedMap. TreeSet and TreeMap are implementations of these traits. The order is determined by the `Ordered` trait in which the element type of the set or key type of map must either mix in or be implicitly convertible to.

```scala
import scala.collection.immutable.TreeSet
val ts = TreeSet(9,3,1,8,0,2,7,4,6,5)
val cs = TreeSet('f', 'u', 'n')
import scala.collection.immutable.TreeMap
val tm = Treemap(3 -> 'x', 1 -> 'x', 4 -> 'x')
```

If you declare a variable as `var`, then collection can be updated with `+=` operation even though it is immutable. In this first a collection is created and then elements are reassigned. The same idea applieds to any method ending in `=`

```scala
val people = Set("Nancy", "Jane")
people += "Bob" // throws error that += method is not supported for immutable Set
var people = Set("Nancy", "Jane")
people += "Bob"
people // Set(Nancy, Jane, Bob)
people -= "Jane"
people ++= List("Tom", "Harry") // Set(Nancy, Bob, Tom, Harry)
```

This means when you want to convert from immutable collection to a mutable one, you only import the mutable collection and the code works fine.

```scala
val capitals = Map("US" -> "Washington", "France" -> "Paris")
capitals += ("Japan" -> "Tokyo")
// To convert to mutable version, only add import scala.collection.mutable.Map at the top
```

If you want to initialize a collection with another collection. For example, load data from list into TreeSet.

```scala
val colors = List("blue", "red", "yellow")
import scala.collection.immutable.TreeSet
val treeSet = TreeSet(colors) // Error
val treeSet = TreeSet[String]() ++ colors // OK
treeSet.toList // List(blue,red,yellow), alphabetical order
treeSet.toArray // Array(blue,red,yellow)
// Converting from immutable to mutable versions
import scala.collection.mutable
val mutaSet = mutable.Set.empty ++= treeSet
val immutaSet = Set.empty ++ mutaSet
```

**Tuples** combine objects of different types and inherit from Traversable. A common application of tuples is returning multiple values from a method.

```scala
def longestWord(words: Array[String]) = {
  var word = words(0)
  var idx = 0
  for (i <- 1 until words.length)
    if(words(i).length > word.length) {
      word = words(i)
      idx = i
    }
  (word, idx)
}
val longest = longestWord("The quick brown fox".split(" "))
longest._1 // quick
longest._2 // 1
val (word, idx) = longest // word: quick, idx: 1
val word, idx = longest // word: (quick, 1), idx: (quick, 1)
```

## Mutable objects

For a mutable object, the result of a method call or field access may depend on what operations were previous performed on the object. 

```scala
class BankAccount {
  private var bal: Int = 0

  def balance: Int = bal

  def deposit(amount: Int) = {
    require(amount > 0)
    bal += amount
  }

  def withdraw(amount: Int): Boolean =
    if (amount < bal) false
    else {
      bal -= amount
      true
    }
}
```

In Scala, every `var` that is a non-private member of some object implicitly defines a getter and a setter method with it. The getter method for `var x` is just named "x", while the setter is named "x_=". The field is always marked `private[this]` which means it can be accessed only from the object that contains it. The getter and setter get the same visiblity as the original var. For instance, following class defines two public vars named hour and minute.

```scala
class Time {
  var hour = 12
  var minute = 0
}
```

This is similar to following class. It is expanded into getter and setter methods.

```scala
class Time {
  private[this] var h = 12
  private[this] var m = 0

  def hour: Int = h
  def hour_=(x: Int) = { h = x }
  def minutes: Int = m
  def minute_=(x: Int) = { m = x }
}
```

You can also define these methods yourself in your class blueprint with some additional checks. It's possible to define a getter and setter without an associated field.

```scala
class Therommeter {
  var celsius: Float = _ // assigns a zero value to that field

  def fahrenheit = celsius * 9 /5 + 32
  def fahrenheit_= (f:Float) = {
    celsius = (f - 32) * 5 / 9
  }
  override def toString = fahrenheit + "F/" + celsius + "C"
}
```

## Type Parameterization

Type parameterization allows to write generic classes and traits. As a result, any particular set instance might be a `Set[String]` or `Set[Int]`. Variance defines inheritance relationships of parameterized type.

A **functional queue** is a data structure with three operations.
1. `head`: returns the first element
2. `tail`: returns queue without first element
3. `enqueue`: returns a new queue with a given element appended at the end

```scala
// sample operations
val q =  Queue(1,2,3)
val q1 = q enqueue 4
q // Queue(1,2,3)
```

A functional queue does not change its contents when an element is appended. Instead, a new queue is returned that contains the element. If Queue were a mutable implementation, the enqueue opration in the second line would affect the contents of q as well q1. But for a functional queue, it affects only q1. Using a `::` operation, a queue is extended at the end, using enqueue. All three operations should operate in constant time. We can implement this in the form List.

```scala
class SlowAppendQueue[T](elems: List[T]) {
  def head = elems.head
  def tail = elems.tail
  def enqueue(x: T) = new SlowAppendQueue(elems ::: List(x))
}
```

In above, `enqueue` operation takes time proportional to the number of elements stored in the queue. For constant time append, we can reverse the order of elements in representation list, so the last element appended comes first in the list.

```scala
class SlowHeadQueue[T](elems: List[T]) {
  def head = elems.last
  def tail = new SlowHeadQueue(elems.init)
  def enqueue(x: T) = new SlowHeadQueue(x :: elems)
}
```

With above implementation, enqueue is constant time, but head and tail are not. They take time proportional to number of elements in the queue. The solution would be to represent a queue by two lists, called leading and trailing. To append element, we cons it to the trailing list, so enqueue is constant time. So, when an initially empty queue is constructed from successive enqueue operations, the trailing list will grow whereas the leading list will stay empty. Then, before the first head or tail operation on empty leading list, the whole trailing list is copied to leading, reversing the order of elements using mirror method.

```scala
class Queue[T](private val leading: List[T], private val trailing: List[T]) {
  private def mirror =
    if (leading.isEmpty)
      new Queue(trailing.reverse, Nil)
    else
      this
  
  def head = mirror.leading.head

  def tail = {
    val q = mirror
    new Queue(q.leading.tail, q.trailing)
  }

  def enqueue(x: T) = 
    new Queue(leading, x :: trailing)
}
```

Just like in Java you can hide a constructor by making it private, in scala, constructor is defined by the class parameters and body. It is possible to hide the primary constructor by adding a private modifier in front of the class parameter list. This constructor can be accesed only from within the class and its companion object.

```scala
class Queue[T] private {
  private val leading: List[T]
  private val trailing: List[T]
}
```

Once constructor is private, to construct an object, we can define either factory method  or use auxiliary constructor.

```scala
def this(elems: T*) = this(elems.toList, Nil)
// factory method
object Queue {
  def apply[T](xs: T*) = new Queue[T](xs.toList, Nil)
}
```

```scala
trait Queue[T] {
  def head: T
  def tail: Queue[T]
  def enqueue(x: T): Queue[T]
}

object Queue {
  def apply[T](xs: T*): Queue[T] = new QueueImpl[T](xs.toList, Nil)
  private class QueueImpl[T](private val leading: List[T], private val trailing: List[T]) extends Queue[T] {
    def mirror = if (leading.isEmpty) new QueueImpl(trailing.reverse, Nil) else this
    def head: T = mirror.leading.head
    def tail: QueueImpl[T] = {
      val q = mirror
      new QueueImpl(q.leading.tail, q.trailing)
    }
    def enqueue(x: T) = new QueueImpl(leading, x :: trailing)
  }
}
```

Instead of private constructor and private members, we can define a trait and define implementation inside companion object to hide the implementation. Here, Queue enables you to specify parameterized types, such as `Queue[String]`. Queue is also called type constructor because you can construct a type with it by specifying a type parameter. If S is subtype of T, then if `Queue[S]` is subtype of `Queue[T]`, then trait Queue is **covariant** in type parameter T. Covariant Queues mean that you could pass a `Queue[String]` which takes a value of `Queue[AnyRef]`. In Scala, generic types are by default nonvariant. You can ask for covariant relationship using `trait Queue[+T] {}`. There is also suffix `-` which indicates **contravariant** subtyping `trait Queue[-T] {}`. This would mean `Queue[S]` is a subtype of `Queue[T]`. Whether a type parameter is covariant, contravariant or nonvariant is called parameter's *variance*. In functional programming, many types are naturally covariant. It hcanges once we introduce mutable data. The + or - are called variance annotations.

```scala
class Queue[+T] (private val leading: List[T], private val trailing: List[T]) {
  def enqueue(U >: T)(x: U) = new Queue[U](leaidng, x :: trailing)
}
```

With syntax "U >: T", it defines that T is the lower bound for U. U is required to be supertype of T. For example if Fruit class has Apple and Orange, we can append Orange to a `Queue[Apple]`. The result will be `Queue[Fruit]`.
There are some cases where contravariance is natural.

```scala
trait OutputChannel[-T] {
  def write(x: T)
}
```

## Abstract 

A member of a class or trait is abstract if the member does not have complete definition in the class. In Scala, besides methods, you can declare abstract fields and even abstract types as members of classes and traits.

```scala
// abstract members examples
trait Abstract {
  type T
  def transform(x: T): T
  val initial: T
  var current: T
}
// Concrete class
class Concrete extends Abstract {
  type T = String
  def transform(x: String) = x + x
  val initial = "hi"
  var current = initial
}
```

Abstract type in Scala means a type declared to be a member of a class or trait, without specifying a definition. An abstract type in Scala is always a member of some class or trait.
An abstract val declartion is of the following form. It gives name and type for a val, but not value.
`val initial: String`

Similarly, abstract method declaration looks like `def initial: String`. However, if `initial` is an abstract val, the client is guaranteed to yield same value when calling `obj.initial`. If it was an abstract method, that guarantee would not hold because in that case, initial could be implemented by a concrete method that returns a different value.

```scala
abstract class Fruit {
  val v: String
  def m: String
}
abstract class Apple extends Fruit {
  val v: String
  val m: String // OK to override a 'def' with a 'val'
}
// Illegal class
abstract class BadApple extends Fruit {
  def v: String // cannot override a 'val' with 'def'
  def m: String
}
```

Abstract var declares a name and a type but not initial value. Vars declared as members of classes come equipped with getter and setter methods. For example, in following example, getter `hour` and setter `hour_=` are implicitly defined.

```scala
trait AbstractTime {
  var hour: Int
  var minute: Int
}
```

Abstract vals let you provide details in a subcalss that are missing in a superclass. For implementing following RationalTrait, we need to implement two abstract val definitions.

```scala
trait RationalTrait {
  val numerArg: Int
  val denomArg: Int
}
new RationalTrait { // create anonymous class that mixes in the trait and defines its body
  val numerArg = 1
  val denomArg = 2
}
```

The values of numerArg and denomArg are not available during the initialization of RationalTrait. The class parameter argument is evaluated before it is passed to the class constructor.
The pre-initialized fields let you initialize a field of a subclass before the superclass is called. To do this, place the field definition in braces before the superclass constructor call.

```scala
new {
  val numerArg = 1 * x
  val denomArg = 2 * x 
} with RationalTrait
```

These are not limited to anonymous classes; they can also be used in objects or named subclasses.

```scala
object twoThirds extends {
  val numerArg = 2
  val denomArg = 3
} with RationalTrait

class RationalClass(n: Int, d: Int) extends {
  val numerArg = n
  val denomArg = d
} with RationalTrait {
  def + (that: RationalClass) = new RationalClass(
    numer * that.denom + that.numer * denom, denom * that.denom
  )
}
```

Sometimes, you may prefer for the system to sort out how things should be initialized. This can be achieved by `lazy`. If it is added to val, the initializing expression on the right-hand side will only be evaluated the first time the val is used.

```scala
object Demo {
  lazy val x = { println("Initializing x"); "done" }
}
Demo.type
Demo.x // This is when x code runs
```

```scala
trait LazyRationaltrait {
  val numerArg: Int
  val denomArg: Int
  lazy val numer = numerArg / g
  lazy val denom = denomArg / g
  override def toString = numer + "/" + denom
  private lazy val g = {
    require(denomarg != 0)
    gcd(numerArg, denomArg)
  }
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
}
```

The lazy vals can free you as a programmer from having to think hard how to arrange val definitions to ensure that everything is defined when it is needed. However, this holds only as long as the initialization of lazy vals neither produces side effects nor depends on them. In the presence of side effects, initialization order starts to matter. Lazy vals are ideal complement to functional objects but less suitable for imperative code.

### Abstract Types

Abstract type (T) declaration is a placeholder for somthing that will be defined concretely in subclasses. Different subclasses can provide different realizations of T.

```scala
class Food
abstract class Animal {
  def eat (food: Food)
}

class Grass extends Food
class Cow extends Animal {
  override def eat(food: Grass) = {} // This is not correct override as argument type is different.
}
```

To ensure correct food is eatable, we need to have a class that depends on Animal that is eating food. Here, SuitableFood is defined such that Food is upper bound. This means that any concrete instantiation of SuitableFood must be a subclass of Food.

```scala
class Food
abstract class animal {
  type SuitableFood <: Food
  def eat(food: SuitableFood)
}

class Grass extends Food
class Cow extends Animal {
  type SuitableFood = Grass
  override def eat (food: Grass) = {}
}
```

In Scala, the inner class is addressed using the expression `Outer#Inner` instead of Java's `Outer.Inner`. The dot (.) syntax is reserved for objects.

```scala
class Outer {
  class Inner
}
val o1 = new Outer
val o2 = new Outer
```

Now, `o1.Inner` and `o2.Inner` are path-dependent types.
When a class inherits from another, the first class is said to be nominal subtype of the other one. It's nominal because each type has a name, and the names are explicitly declared to have a subtyping relationship. Scala supports structural subtyping where you get a subtyping relationship because two types have compatible members. For this, use Scala's refinement types.

Scala has **Enumeration** as a class. To create a new enumeration, you define an object that extends this class. It provides three values for `Color.Red`, `Color.Green` and `Color.Blue`.

```scala
object Color extends Enumeration {
  val Red = Value
  val Green = Value
  val Blue = Value
}
// OR
object Color extends Enumeration {
  val Red, Green, Blue = Value 
}
```

Enumeration defines an inner class named Value. A value such as `Color.Blue` is of type `Color.Value`. It is a path-dependent type with Color being the dependent type. From below code `Direction.Value` is different from `Color.Value`.

```scala
object Direction extends Enumeration {
  val North, East, South, West = Value
}
// associate names with enumeration values by using overloaded variant of Value
object Direction extends Enumeration {
  val North = Value("North")
  val East = Value("East")
  val South = Value("South")
  val West = Value("West")
}
// Iterate over values
for (d <- Direation.values) print (d + " " )
Direction.East.id // 1, Values are numbered from 0
Direction(1) // Direction.Value = East
```

### Designing Currencies

```scala
abstract class Currency {
  val amount: Long
  def designation: String
  override def toString = amount + " " + designation
  def + (that: Currency): Currency
  def * (x: Double): Currency
}
// You can create currency object as follows
new Currency {
  val amount = 70L
  def designation = "USD"
}
```

Now based on this, if we want to implement several currencies, the addition of such currencies should be consistent. That means the addition operation should take Dollar for dollar objects and Euro for euros objects. In such case, we can make them abstract.

```scala
abstract class AbstractCurrency {
  type Currency <: AbstractCurrency
  val amount: Long
  def designation: String
  ... ...
}
abstact class Dollar extends AbstractCurrency {
  type Currency = Dollar
  def designation = "USD"
}
```

Now, the issue is how do you add two curerncies? For that, we can implement abstract factory method.

```scala
abstract class CurrencyZone {
  type currency <: AbstractCurrency
  def make(x: Long): Currency
  abstract class AbstractCurrency {
    val amount: Long
    def designation: String
    override def toString = amount + " " + designation
    def + (that: Currency): Currency = make(this.amount + that.amount)
    def * (x: Double): Currency = make((this.amount * x).toLong)
  }
}
// concrete CurrencyZone US
object US extends CurrencyZone {
  abstract class Dollar extends AbstractCurrency {
    def designation = "USD"
  }
  type Currency = Dollar
  def make (x: Long) = new Dollar{ val amount = x }
}
```

Now, every currency also has subunits like cents.

```scala
object US extends CurrencyZone {
  abstract class Dollar extends AbstractCurrency {
    def designation = "USD"
  }
  type Currency = Dollar
  def make(cents: Long) = new Dollar {
    val amount = cents
  }
  val Cent = make(1)
  val Dollar = make(100)
  val CurrencyUnit = Dollar
}
```

We could also add a currency conversion to the model. We could write a Converter object that contains applicable exchange rates between currencies. Then a conversion method from, to class Currency.

```scala
def from(other: CurrencyZone#AbstractCurrency): Currency =
  make(math.round(
    other.amount.toDouble * converter.exchangeRate (other.designation)(this.designation)
  ))
```

Final version looks as follows.

```scala
object Europe extends CurrencyZone {
  abstract class Euro extends AbstractCurrency {
    def designation = "EUR"
  }
  type Currency = Euro
  def make (cents: Long) = new Euro {
    val amount = cents
  }
  val Cent = make(1)
  val Euro = make(100)
  val CurrencyUnit = Euro
}

object Japan extends CurrencyZone {
  abstract class Yen extends AbstractCurrency {
    def designation = "JPY"
  }
  type Currency = Yen
  def make (yen: Long) = new Yen {
    val amount = yen
  }
  val Yen = make(1)
  val CurrencyUnit = Yen
}
// Object converter
object Converter {
  var exchangeRate = Map (
    "USD" -> Map("USD" -> 1.0, "EUR" -> 0.7598, "JPY" -> 1.221, "CHF" -> 1.223),
    "EUR" -> Map("USD" -> 1.316, "EUR" -> 1.0, "JPY" -> 1.594, "CHF" -> 1.623),
    "JPY" -> Map("USD" -> 0.8259, "EUR" -> 0.6272, "JPY" -> 1.0, "CHF" -> 1.018),
    "CHF" -> Map("USD" -> 0.8108, "EUR" -> 0.6160, "JPY" -> 0.982, "CHF" -> 1.0)
  )
}
// CurrencyZone
abstract class CurrencyZone {
  type Currency <: AbstractCurrency
  def make(x: Long): Currency
  abstract class AbstractCurrency {
    val amount: Long
    def designation : String
    def + (that: Currency): Currency = make(this.amount + that.amount)
    def * (x: Double): Currency = make((this.amount * x).toLong)
    def - (that: Currency): Currency = make(this.amount - that.amount)
    def / (that: Currency) = this.amount.toDouble / that.amount

    def from(other: CurrencyZone#AbstractCurrency): Currency =
      make (math.round(
        other.amount.toDouble * Converter.exchangeRate (other.designation)(this.designation)
      ))
    private def decimals(n: Long): Int = if (n == 1) 0 else 1 + decimals(n / 10)
    override def toString = ((amount.toDouble / CurrencyUnit.amount.toDouble) formatted "%. " + decimals(CurrencyUnit.amount) + "f " + designation)
  }
  val CurrencyUnit: Currency
}

// tests
Japan.Yen from US.Dollar * 100
Europe.Euro from res1
US.Dollar from res2
```

## Implicit Conversions

You cannot modify libraries and code written by other people out there. To alleviate this, Scala has implicit conversions and parameters. Implicit conversions are helpful for working with two bodies of software that were developed without each other in mind. Implicit conversions help by reducing the number of explicit conversions that are needed from one type to another. Without implicit conversions, a Scala program that uses Swing must use inner classes like Java.

```scala
val button = new JButton
button.addActionListener (new ActionListener {
  def actionPerformed(event: ActionEvent) = {
    println("Button pressed")
  }
})
```

We can reduce the coding using function listerals.

```scala
button.addActionListener(
  (_: ActionEvent) => println("Button pressed")
)
```

But above code doesn't work because `addActionListener` expects `ActionListener` and we passed function. With implicit conversions, this can be made to work.

```scala
// one argument method that takes function and returns ActionListener
implicit def function2ActionListener(f: ActionEvent => Unit) =
  new ActionListener {
    def actionPerformed(event: ActionEvent) = f(event)
  }
```

Now following two code versions work. The second version works beacuse `function2ActionListener` is marked as implicit, it can be left out and the compiler will insert it automatically.

```scala
// without implicit
button.addActionListener (
  function2ActionListener (
    (_: ActionEvent) => println("Button pressed")
  )
)
// with implicit conversion
button.addActionListener (
  (_: ActionEvent) => println("Button pressed")
)
```

- Implicit definitions are those that the compiler is allowed to insert into a program in order to fix any of its type errors. For example, if `x + y` does not type check, the compiler might change it to `conver(x) + y`, where convert is some available implicit conversion. 
- Only definitions marked implicit are available. An inserted implicit conversion must be in scope as a single identifier, or be associated with the source or target type of conversion. Therefore, you must bring implicit conversion in scope. 
- The compiler will also look for impliciit definitions in the companion object of the scope or expected target types of the conversion. If you're attempting to pass a Dollar to method that takes Euro object, the source is Dollar and target is Euro. Implicit conversion could be packaged in either object or either class, Dollar or Euro.
- Only one implicit is inserted. The compiler will never rewrite `x + y` to `conver1(convert2(x)) + y`. This could increase compile time.
- Explicits are tried first. The compiler will not change the code that already works.
- Implicit conversions can have arbitrary names. If one object defines two conversions and you want to use only one of them, then you need to know the explicit name of the conversion method to import that one only.
- There are three places implicits are used in language: conversion to an expected type, conversions of the receiver of a selection and implicit parameters

Implicit conversion to an expected type is the first place the compiler will use implicits. For example, normally a double cannot be used as an integer, but with implicit function it can be.

```scala
val i: Int = 3.5 // Error
implicit def doubleToInd(x: Double) = x.toInt
val i: Int = 3.5 // OK
```

Implicit conversions also apply to the receiver of a method call, the object on which the method is invoked.

```scala
class Rational(n: Int, d: Int) {
  def + (that: Rational): Rational = ...
  def + (that: Int): Rational = ...
}

val oneHalf = new Rational(1, 2)
oneHalf + oneHalf
oneHalf + 1
1 + oneHalf // Error
implicit def intToRational(x: Int) = new Rational(x, 1)
1 + oneHalf // OK
```

The other major use of implicit conversions is to simulate adding new syntax. For example, Maps can be created using arrow syntax (->). When you see someone calling methods that appear not to exist in the receiver class, they are probably using implicits. If you see a class named RichSomething, that class is likely adding syntax like methods to type Something.

**Implicit classes** were added in Scala 2.10 to make it easier to write rich wrapper classes. An implicit class is a class that is preceded by `implicit` keyword. For such class, compiler generates an implicit conversion from class's constructor parameter to the class itself. For example, if you have Rectangle class which you use frequently, then you need to use rich wrappers so you can more easily construct it.

```scala
case class Rectangle (width: Int, height: Int)
implicit class RectangleMaker(width: Int) {
  def x(height: Int) = Rectangle(width, height)
}
// Following conversion is automatically generated
implicit def RectangleMaker(width: Int) = new RectangleMaker(width)
// you can use it like this
val myRectangle = 3 x 4
```

Since Int has no method named `x`, it will look for implicit conversion from Int to something and will find RectangleMaker conversion which has that method.
An implicit class cannot be a case class, and its constructor must have exactly one parameter. An implicit class must be located within the some other object, class or trait.

The compiler will sometimes replace `someCall(a)` with `someCall(a)(b)` or `new SomeClass(a)` thereby adding a missing parameter list to complete a function call. If someCall is missing last parameter list takes three parameters, the compiler might replace `someCall(a)` with `someCall(a)(b,c,d)`. For this last three paramters must be marked implicit where they are defined, but also the last parameter list in someCall be defined as implicit.

```scala
class PreferredPrompt(val preference: String)
object Greeter {
  def greet(name: String)(implicit prompt: PreferredPrompt) = {
    println("Welcome, " + name + ". The system is ready.")
    println(prompt.preference)
  }
}
val bobsPrompt = new PreferredPrompt("relax> ")
Greeter.greet("Bob")(bobsPrompt)
// To let compiler supply the parameter implicitly, define a variable of expected type in PreferredPrompt
object JoesPrefs {
  implicit val prompt = new PreferredPrompt("Yes, master> ")
}
import JoesPrefs._
Greeter.greet("Joe")

// two implicit parameters example
class PreferredPrompt(val prefernence: String)
class PreferredDrink(val preference: String)
object Greeter {
  def greet(name: String)(implicit prompt: PreferredPrompt, drink: PreferredDrink) = {
    println(s"Welcome, $name. The system is ready.")
    println("Enjoy your cup of " + drink.preference + ".")
    println(prompt.preference)
  }
}
object JoesPrefs {
  implicit val prompt = new PreferredPrompt("Yes, master> ")
  implicit val drink = new PreferredDrink("tea")
}
// another example of finding max
def maxListOfOrdering[T](elements: List[T])(implicit ordering: Ordering[T]): T =
  elements match {
    case List() => throw new IllegalArgumentException("empty list")
    case List(x) => x
    case x: rest => 
      val maxRest = maxListOfOrdering(rest)(ordering)
      if (ordering.gt(x, maxRest)) x
      else maxRest
  }
maxListOfOrdering(List(2,3,10,3)) // 10
```

When you use implicit on a parameter, the compiler will not only try to supply that parameter with an implicit value, but the compiler will also use that parameter as an available implicit in the body of the method. If we have a method implicitly like `def implicitly[T](implicit t:T) = t`, then the method can change as below.

```scala
def maxList[T: Ordering](elements: List[T]): T =
  elements match {
    case List() => throw new IllegalArgumentException("empty list")
    case List(x) => x
    case x: rest => 
      val maxRest = maxList(rest)
      if (implicitly[Ordering[T]].gt(x, maxRest)) x
      else maxRest
  }
```

If multiple implicit conversions are in scope, mostly Scala refuses to insert a conversion in such a case. When implicit conversions are ambiguous, they are not applied. 
