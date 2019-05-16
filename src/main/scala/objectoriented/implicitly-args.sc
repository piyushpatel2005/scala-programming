import math.Ordering
case class MyList[A](list: List[A]) {
  // List.sortBy takes a function that transforms the arguments into something that satisfies math.Ordering
  // An implicit argument is required that knows how to order instances of type B
  def sortBy1[B](f: A => B)(implicit ord: Ordering[B]): List[A] = list.sortBy(f)(ord)

  def sortBy2[B: Ordering](f: A => B): List[A] = list.sortBy(f)(implicitly[Ordering[B]])
}

val list = MyList(List(1,3,5,2,4))

list sortBy1(i => -i)
list sortBy2(i => -i)