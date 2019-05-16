import scala.language.implicitConversions

case class Foo(s: String)
object Foo {
//  conversion from string
  implicit def fromString(s: String): Foo = Foo(s)
}

class O {
  def m1(foo: Foo) = println(foo)
  // caling m1 with String, but it expects Foo data type.
  // The compiler finds the Foo.fromString conversion even though we didn't import it explicitly into the scope of O class.
  // If there was another Foo conversion in scope, it will override Foo.fromString conversion
  def m(s: String) = m1(s)
}