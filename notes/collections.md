# List Implementation

Lists are defined by an abstract class `List` in scala package with two subclasses for :: and Nil. Lists are covariant.

```scala
package scala
abstract class List[+T] {
}
val xs = List(1, 2, 3)
var ys: List[Any] = xs
```

All list operations can be defined in terms of three basic methods. These are defined in the sub object Nil and the subclass ::. The `Nil` object defines empty list and inherits from type `List[Nothing]`.

```scala
def isEmpty: Boolean
def head: T
def tail: List[T]
```

```scala
case object Nil extends List[Nothing] {
  override def isEmpty = true
  def head: Nothing = throw new NoSuchElementException("head of empty list")
  def tail: List[Nothing] = throw new NoSuchElementException("tail of empty list")
}
```

The cons class (::) represents non-empty lists.

```scala
final case class ::[T](hd: T, tl: List[T]) extends List[T] {
  def head = hd
  def tail = tl
  override def isEmpty: Boolean = false
  def length: Int = if (isEmpty) 0 else 1 + tail.length
  def drop(n: Int): List[T] = 
    if (isEmpty) Nil
    else if (n <= 0) this
    else tail.drop(n - 1)
  def map[U](f: T => U): List[U] =
    if (isEmpty) Nil
    else f(head) :: tail.map(f)
}
```

An operation `x :: xs` is treated as the method call `xs.::(x)`.
**List Buffers** let you accumulate the elements of a list.

```scala
import scala.collection.mutable.ListBuffer
val buf = new ListBuffer[Int]
for (x <- xs) buf += x + 1
buf.toList
```

Constructing lists with :: re-uses the tail of the constructed list.

```scala
val ys = 1 :: xs
val zs = 2 :: xs
```

The tails of ys and zs are shared; they point to same data structure. If the list xs was copied every time you added a new element onto it, this would be much slower. The ListBuffer still allows you to build up lists imperatively and incrementally, if you want. You can either construct lists incrementally by adding elements to the beginning using :: or use a list buffer for adding elements to the end.

## For Expressions

```scala
case class Person(name: String, isMale: Boolean, children: Person*)
val lara = Person("Lara", false)
val bob = Person("Bob", true)
val julie = Person("Julie", false, lara, bob)
val persons = List(lara, bob, julie)
persons filter (p => !p.isMale) flatMap (p => (p.children map (c => (p.name, c.name)))) // List((Julie, Lara), (Julie, Bob))
// same could be done more efficiently
persons withFilter (p => !p.isMale) flatMap (p => (p.children map (c = > (p.name, c.name))))
// easier way to write the same with for expression
for (p <- persons; if !p.isMale; c <- p.children)
  yield (p.name, c.name)
```

All for expressions that yield a result are translated by the compiler into combinations of invocations of the higher-order methods map, flatMap and withFilter.
Generally, for expression is of the form: `for (seq) yield expr`.

```scala
for (p <- persons; n = p.name; if (n startsWith "To"))
  yield n
```

This for expression contains one generator, one definition and one filter. The sequence could be enclosed in braces instead of parentheses.

```scala
for {
  p <- persons              // a generator
  n = p.name                // a definition
  if (n startsWith "To")    // a filter
} yield n
```

If there are several generators in a for expression, later generators vary more rapidly than earlier ones.

```scala
for (x <- List(1,2); y <- List("one", "two")) yield (x, y)
// List((1,one), (1, two), (2, one), (2, two))
```

The for expression is equivalent to common operations of database query languages.

```scala
case class Book(title: String, authors: String*)
val books: List[Book] = List(
  Book("Structure andInterpretation of Compute programs", "Abelson, Harold", "Sussman, Gerald J."),
  Book("Principles of Compiler Design", "Aho, Alfred", "Ullman, Jeffrey"),
  Book("Programming in Modula-2", "Wirth, Niklaus"),
  Book("Elements of ML Programming", "Ullman, Jeffrey"),
  Book("The Java Language Specification", "Gosling, James", "Joy, Bill", "Steele, Guy", "Bracha, Gilad")
)
// find titles whose author's last name is "Gosling"
for (b <- books; a <- b.authors
    if a startsWith "Gosling")
yield b.title // List(The Java Language Specification)
// find the titles that have the string "Program" in title
for (b <- books if (b.title indexOf "Program") >= 0)
yield b.title // List(Structure and Interpretation of Computer Programs, Programming in Modula-2, Elements of ML Programming)
// find names of all authors who have written at least two books
for (b1 <- books; b2 <- books if b1 != b2;
    a1 <- b1.authors; a2 <- b2.authors if a1 == a2)
yield a1 // List(Ullman, Jeffrey, Ullman, Jeffrey)
```

The following for expression gets converted into below function calls.

```scala
for (b1 <- books; b2 <- books if b1 != b2;
    a1 <- b1.authors; a2 <- b2.authors if a1 == a2)
yield a1
// this is equivalent to following
books flatMap (b1 => 
  books withFilter (b2 => b1 != b2) flatMap (b2 =>
    b1.authors flatMap(a1 => 
      b2.authors withFilter(a2 => a1 == a2) map (a2 => a1))))
```

```scala
for (pat <- expr1) yield expr2
// is equivalent to following
expr1 withFilter {
  case pat => true
  case _ => false // never throw MatchError as it is first filtered
} map {
  case pat => expr2
}
```

```scala
// sum of all elements of a matrix
var sum = 0
for (xs <- xss; x <- xs) sum += x
// It translates into nested foreach loop
var sum = 0
xss foreach (xs =>
  xs foreach (x => 
    sum += x))
// Implementing map, flatMap and filter methods using for expression
def map[A, B](xs: List[A], f: A => B): List[B] =
  for (x <- xs) yield f(x)
def flatMap[A, B](xs: list[A], f: A => List[B]): List[B] =
  for (x <- xs; y <- f(x)) yield y
def filter[A](xs: List[A], p: A => Boolean): List[A] =
  for (x <- xs if p(x)) yield x
```

Lists, arrays, ranges, iterators, streams, sets all support for expressions. If your own data type defines map, flatMap, foreach and withFilter oprerations, that can also support for expressions. The translation of for expressions happen before type checking. Below are type signatures of different methods.

```scala
abstract class C[A] {
  def map[B](f: A => B): C[B]
  def flatMap[B](f: A => C[B]): C[B]
  def withFilter(p: A => Boolean): C[A]
  def foreach(b: A => Unit): Unit
}
```

## Collections

Scala provides mutable and immutable collections. A mutable collection can be updated or extended in place. Immutable collections never change. By default, Scala picks immutable collections. At the top of the collection hierarchy is trait **Traversable**. It has only abstract method foreach `def foreach[U](f: Elem => U)`. Traversable also defines concrete methods for `++`, `map`, `flatMap`, `collect`, `toIndexedSeq`, `toIterable`, `toStream`, `toArray`, `toList`, `toSeq`, `toSet`, `toMap`, `copyToBuffer` and `copyToArray`, `isEmpty`, `nonEmpty`, `size`, `head`, `last`, `headOption`, `find`, etc.

| Methods of Traversable | Description |
|:--------:|:-----------|
| `xs foreach f` | Execute function f for every element of xs |
| `xs ++ ys` | combine two collections |
| `xs map f` | return collection obtained by applying f on every element of xs |
| `xs flatMap f` | apply collection-valued function f to every element in xs and concatenating the results |
| `xs collect f` | collection obtained from applying partial function f to every element in xs |
| `xs copyToBuffer buf` | copies all elements of collection to buffer buf. |
| `xs copyToArray(arr, s, len)` | copies at most len elements of arr, starting at index s. |
| `xs.isEmpty` | Tests whether collection is empty |
| `xs.nonEmpty` | tests whether collection is non empty |
| `xs.size` | number of elements in collection |
| `xs.hasDefiniteSize` | check if collection has finite size |
| `xs.head` | first element of collection |
| `xs.headOption` | first element in an Option value |
| `xs.last` | last element of xs |
| `xs.lastOption` | last element of xs in an Option value |
| `xs find p` | An option containing the first element in xs that matches p or None |
| `xs.tail` | the rest of collection except first |
| `xs.init` | the rest of collection except last |
| `xs slice (from, to)` | a collection of elements between index range |
| `xs take n` | a collection of first n elements of xs |
| `xs drop n` | the rest of collection except first n |
| `xs takeWhile p` | The longest elements collection as long as it satisfies predicate p |
| `xs dropWhile p` | The collection of elements without `xs takeWhile p`. |
| `xs filter p` | The collection consisting of elements that satisfy predicate p. |
| `xs withFilter p` | |
| `xs filterNot p` | Collection consisting of elements of xs that do not satisfy the predicate p. |
| `xs splitAt n` | Splits xs at a position, giving pair of collections. |
| `xs span p` | splits xs according to a predicate until first false, giving a pair of collections (`xs takeWhile p`, `xs dropWhile p`). |
| `xs partition p` | Splits xs into a pair of collections based on predicate. (`xs filter p`, `xs filterNot p`) |
| `xs groupBy f` | Partitions xs into a map of collections according to function f. |
| `xs forall p` | Checks if predicate p holds for all elements of xs. |
| `xs exists p` | Checks if predicated p holds for any element of xs. |
| `xs count p` | number of elements that satisfy the predicate p. |
| `(z /: xs)(op)` | applies binary operation `op` between successive elements of xs, going from left to right, starting with z. |
| `(xs :\ z)(op)` | Same as above operation but going right to left. |
| `xs.foldLeft(z)(op)` | Same as `(z /: xs)(op)`. |
| `xs.foldRight(z)(op)` | Same as `(xs :\ z)(op)`. |
| `xs reduceLeft op` | applies binary operation op going left to right |
| `xs reduceRight op` | applies binary operation op going right to left |
| `xs.sum` | sume of all elements |
| `xs.product` | product of all elements |
| `xs.min` | minimum in a collection |
| `xs.max` | maximum in a collection |

The next trait is **Iterable** which has `iterator` method which yields the collection's elements one by one. Most implementations of Iterable provide overridden implementation of foreach method. The `grouped` method chunks its elements into increments, `sliding` method yields a sliding window over the elements.

```scala
val xs = List(1,2,3,4,5)
val git = xs grouped 3
git.next() // List(1,2,3)
git.next(4,5)
val sit = xs sliding 3
sit.next() // List(1,2,3)
sit.next(2,3,4)
sit.next(3,4,5)
```

Scala provides two traits Traversable and Iterable because in some cases it may be easier to define methods in terms of foreach instead of iterator. For example, for implementing Binary tree.

```scala
sealed abstract class Tree
case class Branch(left: Tree, right: Tree) extends Tree
case class Node(elem: Int) extends Tree
sealed abstract class Tree extends Traversable[Int] {
  def foreach[U](f: Int => U) = this match {
    case Node(elem) => f(elem)
    case Branch(l, r) => l foreach f; r foreach f
  }
}
```

| Methods of Iterable | Description |
|:-------------------:|:------------|
| `xs.iterator` | An iterator that yields every element in xs. |
| `xs grouped size` | iterator that yields fixed-sized chunks of this collection |
| `xs sliding size` | An iterator that yields a sliding fixed-sized window of elements in the collection. |
| `xs takeRight n` | collection consisting of last n elements |
| `xs dropRight n` | collection without last n elements |
| `xs zip ys` | An iterable of pairs of elements from xs and ys |
| `xs zipAll (ys, x, y)` | An iterable of pairs of corresponding elements from xs and ys, where the shorter sequence is extended to match the longer one by appending element x or y. |
| `xs.zipWithIndex` | An iterable of pairs of elements from xs with their indices |
| `xs sameElements ys` | Test whether xs and ys contain the same elements in same order. |

Seq, Set and Map inherit from Iterable. They all implement the PartialFunction trait with its apply and isDefinedAt methods. For example, `Seq(1,2,3)(1) == 2`. A **Seq** is a kind of iterable that has length and whose elements have fixed index positions, starting from 0. `Seq[T]` is a partial function that takes an Int argument (an index) and yields a sequence element of that type T. `Seq[T]` extends `PartialFunction[Int, T]`. The `lengthCompare` method compares the lengths of two sequences. The `updated` method updates a sequence and returns a new sequence.

| Sequence Methods | Description |
| `xs(i)` | the element of xs at index i |
| `xs isDefinedAt i` | Tests whether i is contained in `xs.indices`. |
| `xs.length` | length of sequence |
| `xs.lengthCompare ys` | Returns -1 if xs is shorter than ys, +1 if longer else 0 |
| `xs.indices` | the index range of xs |
| `xs indexOf x` | The index of the first element in xs equal to x. |
| `xs lastIndexOf x` | The last index of x in xs. |
| `xs indexOfSlice ys` | Find index of xs such as that ys elements match the sequences from that index in xs. |
| `xs lastIndexOfSlice ys` | Same as above but from last element. |
| `xs indexWhere p` | The index of first element in xs that satisfies predicate p. |
| `xs segmentLength(p, i)` | The length of the longest uninterrupted segment of element in xs, starting with xs(i) that all satisfy the predicate p. |
| `x +: xs` | A new sequence consist of x prepended to xs. |
| `xs :+ x` | A new sequence that consists of x appended to xs. |
| `xs padTo (len, x)` | The sequence resulting from appending x to xs until length len is reached. |
| `xs patch (i, ys, r)` | The sequence resulting from replacing r elements of xs starting with i by the patch ys. |
| `xs updated (i, x)` | A copy of xs with the element at index i replaced by x. |
| `xs.sorted` | A new sequence obtained by sorting the elements of xs using standard ordering of the element. |
| `xs sortWith lessThan` | A new sequence obtained by sorting the elements of xs, using lessThan as comparison operation. |
| `xs sortBy f` | A new sequence obtained by sorting the elements of xs using mapping function f and comparing the result. |
| `xs.reverse` | A sequence with the elements of xs in reverse order. |
| `xs.reverseIterator` | An iterator yielding all elements in reverse order. |
| `xs.reverseMap f` | A sequence obtained by mapping f over the elements of xs in reverse order. |
| `xs startsWith ys` | Test whether xs starts with sequence ys |
| `xs endsWith ys` | Test whether xs ends with sequence ys |
| `xs contains x` | test whether xs has an element x. |
| `xs containsSlice ys` | tests whether xs has a contiguous subsequence ys |
| `xs intersect ys` | Intersection of sequences and preserves order of elements in xs. |
| `xs diff ys` | Difference of sequences xs and ys, preserves order of xs. |
| `xs union ys` | Union |
| `xs.distinct` | A subsequence of distinct elements. |

Each Seq trait has two subtraits, `LinearSeq` and `IndexedSeq`. A linear sequence has efficient head and tail operations, whereas indexed sequence has efficient apply, length and update operations. The Vector class provides compromise between indexed and linear access. Therefore, vectors are good for mixed access patterns.

A subcategory of mutable sequences is **buffers**. Buffers allow to update, insert or removal and efficient additions of new elements to the end of the buffer. 

| Methods of Sequence | Description or return data  |
| `buf += x` | Append element x to buffer buf and returns buf |
| `buf += (x, y, z)` | appends given elements to buffer. |
| `buf ++ xs` | apppends all elements in xs to buffer |
| `x +=: buf` | Prepends element x to buffer. |
| `xs ++=: buf` | Prepends all elements from xs to buffer |
| `buf insert (i, x)` | Insert element x at index i in buffer |
| `buf insertAll(i, xs)` | Insert all elements of xs at index i |
| `buf -= x` | Remove element x from buffer |
| `buf remove i` | Remove element at index i |
| `buf trimStart n`  | Removes first n elements from buffer |
| `buf trimEnd n` | Removes last n elements |
| `buf.clear()` | Removes all elements from buffer |
| `buf.clone` | A new buffer with same elements as buf |

**Sets** are iterables that contain no duplicate elements. The apply method for Scala is same as contains method.

```scala
val fruits = Set("apple", "orange", "peach", "banana")
fruits("peach") // true
fruits("potato") // false
```

| Methods of Set | Description or Returned value |
|:--------------:|:------------------------------|
| `xs contains x` | Test whether x is element of xs |
| `xs subsetOf ys` | Test whether xs is a subset of ys |
| `xs + x` | The set containing all elements of xs as well as x |
| `xs + (x, y, z)` | The set of all elements plus given additional elements |
| `xs ++ ys` | The set of all elements from xs and ys |
| `xs - x` | Remove x from xs and return new set |
| `xs- (x,y,z)` | Return set without those three elements |
| `xs -- ys` | Set contains all elements of xs except the elements of ys |
| `xs.empty` | An empty set |
| `xs & ys` OR `xs intersect ys` | set intersection of xs and ys |
| `xs union ys` OR `xs | ys` | Union of xs and ys |
| `xs &~ ys` OR `xs diff ys` | Set difference of xs and ys |

The choice of method names `+=` and `-=` means the samee code can work with mutable or immutable sets. So, mutable collection stored in val can be replaced by immutable collection stored in a var.

**Maps** are Iterables of pairs of keys and values.

| Methods of Map | Description or Returned value |
|:--------------:|:------------------------------|
| `ms get k` | The value associated with key k in map ms as Option. |
| `ms(k)` | The value associated with key k or throw exception if not found. |
| `ms getOrElse(k,d)` | The value at key k else return default value d. |
| `ms contains k` | Tests whether ms contains a mapping for key k |
| `ms + (k -> v)` | returns new map with key-value pair added. |
| `ms isDeifnedAt k` | Same as contains |
| `ms + (k -> v, l -> w)` | Add multiple key-value pairs |
| `ms ++ kvs` | add key/value pairs of kvs. |
| `ms updated (k,v)` | same as `ms + (k -> v)` |
| `ms - k` | Remove mapping for key k |
| `ms - (k,l,m)` | Remove multiple mappings from map. |
| `ms -- ks` | Remove multiple mappings from another keyset ks |
| `ms.keys` | An iterable of each key in ms. |
| `ms.keySet` | A set containing each in ms |
| `ms.keysIterator` | An iterator with each key in ms |
| `ms.values` | An iterable with each value from map |
| `ms.valuesIterator` | An iterator with each value with a key in ms |
| `ms filterKeys p` | A map view containing only keys that satisfies predicate p. |
| `ms mapValues f` | Map values using a function f |
| `ms(k) = v` | Adds mapping from key k to value v to map, overwriting any previous mapping of k. |
| `ms += (k -> v)` | Adds mapping for key k and returns ms |
| `ms put (k,v)` | Adds mapping for key k and returns previously associated value with k as an option |
| `ms getOrElseUpdate(k,d)` | If key k is defined in map ms, returns its associated value. Otherwise, updates ms with the mapping `k->d` and returns d. |
| `ms -= k` | removes mapping with key k from ms as a side effect and returns ms |
| `ms remove k` | Removes any mappings for k and returns previously associated value. |
| `ms retain p` | Keeps onoly those mapping in ms that have a key satisfying predicate p. |
| `ms.clear()` | Removes all mappings from ms |
| `ms transform f` | Transforms all associated values in map ms with function f |
| `ms.clone` | Returns new mutable map with the same mappings as ms |

The function `getOrElseUpdate` is useful for accessing maps that act as caches. For example,

```scala
def f(x: String) = {
  // represents expensive computation that will be cached.
  println("Taking long time"); Thread.sleep(100)
  x.reverse
}
val cache = collection.mutable.Map[String, String]()
def cachedF(s: String) = cache.getOrElseUpdate(s, f(s)) // second argument is by-name, so the computation of f(s) is only performed if getOrElseUpdate  requires the value.
```

A **Stream** is like a list but their elements are computed lazily. That's why they can be infinitely long. Lists are constructed using `::` whereas streams are constructed using `#::` operator.

```scala
val str = 1 #:: 2 #:: # 3:: Stream.empty
// fibonacci sequence
def fibFrom(a: Int, b: Int): Stream[Int] = a #:: fibFrom(b, a+b)
```

Lists are very efficient when the algorithm processing them is careful to only process their heads. Accessing or modifying elements later in the list takes linear time. **Vectors** give efficient access to elements beyond the head. Vectors are represented as broad, shallow trees. Each tree node contains upto 32 elements or contains upto 32 other tree nodes. Vectors are immutable, so you cannot change an element of a vector in place. However, with the `updated` method you can create a new vector that differs from a given vector only in a single element.

```scala
val vec = scala.collection.immutable.Vector.empty
val vec2 = vec :+ 1 :+ 2
val vec3 = 100 +: vec2 // Vector(100, 1, 2)
vec3(0) // 100

val vec = Vector(1,2,3)
vec updated(2,4) // Vector (1,2,4)
vec // Vector (1,2,3) remains the same
```

For last-in-first-out sequences, we can use a **Stack**. Immutable stacks are rarely used in Scala as their push is replaced by `::` on list and pop is same as tail on a list.

```scala
val stack = scala.collection.immutable.Stack.empty
val hasOne = stack.push(1)
stack // Stack()
hasOne.top // 1
hasOne.pop // Stack()
```

**Queue** is similar to stack but it is first-in-first-out rather than LIFO.

```scala
val empty = scala.collection.immutable.Queue[Int]()
val has1 = empty.enqueue(1) // Queue(1)
val has123 = has1.enqueue(List(2,3)) // Queue(1,2,3)
val (element, has23) = has123.dequeue // element = 1, has23 = Queue(2,3)
```

A **range** is an ordered sequence of integers that are equally spaced apart.

```scala
1 to 3 // Range(1,2,3)
5 to 14 by 3 // Range(5,8,11,14)
1 until 3 // exclude upper limit Range(1,2)
```

**Hash tries** are standard way to implement immutable sets and  maps efficiently. Their representation is similar to vectors, but selection is done based on a hash code. For example, to find a given key in a map, you use the lowest five bits of the hash code of the key to select first subtree, the next five bits the next subtree and so on. They strike nice  balance between fast lookups and reasonably efficient functional insertions and deletions. That's why they are default implementations of immutable maps and sets. Scala has further optimization for sets and maps that contain less than five elements. Sets and maps with one to four elements are stored as single objects that just contain the elements as fields.

**Red-black trees** are a form of balanced binary trees where some nodes are designed red and others black. Scala provides implementations of sets and maps that use a red-black tree internally. They are standard implementation of SortedSet because they provide efficient iterator that returns all elements in sorted order.

```scala
val set = collection.immutable.Treeset.empty[Int]
set + 1 + 3 + 3 // TreeSet(1,3)
```

A **bit set** represents a collection of small integers as the bits of a larger integer. Internally, bit sets use an array of 64-bit Longs. Operations on bit sets are very fast.

```scala
val bits = scala.collection.immutable.BitSet.empty
val moreBits = bits + 3 + 4 + 4
moreBits(3) // true
moreBits(0) // false
```

A **list map** represents a map as a linked list of key-value pairs. List map might have to iterate through the entire list. There is little usage for list maps because standard immutable maps are almost always faster.

```scala
val map = collection.immutable.ListMap(
  1 -> "one", 2 -> "two"
)
map(2) // two
```

### Concrete Mutable Collection Classes

An **array buffer** holds an array and a size. It has almost same speed as an array. Array buffers can have data efficiently added to the end. Appending an item takes amortized constant time.

```scala
val buf = collection.mutable.ArrayBuffer.empty[Int]
but += 1
buf += 10
buf.toArray // Array(1,10)
```

A **list buffer** is like an array buffer except that it uses a linked list internally instead of an array. If a buffer needs to be converted to list once it is built, use a list  buffer instead of array buffer.

```scala
val buf = collection.mutable.ListBuffer.empty[Int]
buf += 1
buf += 10
buf.toList // List(1,10)
```

A String bulder is useful for bulding strings. 

```scala
val buf = new StringBuilder
buf += 'a'
buf ++ "bcdef"
buf.toString
```

Mutable collections also incldue LinkedList, DoubleLinkedLists, MutableList, Queue, ArraySeq, Stack, ArrayStack, HashMap, BitSet, etc.


**Arrays** are special in Scala. Arrays can be generic and can support all sequence operations.

```scala
val a1 = Array(1,2,3)
val a2 = a1 map (_ * 3)
val a3 = a2 filter (_ % 2 != 0)
a3.reverse
```

Scala arrays are represented just like Java array, but additional features are added using implicit conversions. Thus arrays are compatible with sequences because there's an implicit conversion from Array to WrappedArray.
407