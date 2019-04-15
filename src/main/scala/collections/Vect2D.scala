package collections

class Vect2D (val x: Double, val y: Double) {

  def +(v: Vect2D): Vect2D = {
    new Vect2D(x + v.x, y + v.y)
  }

  def -(v: Vect2D) = new Vect2D(x - v.x, y - v.y)

  def *(c: Double) = new Vect2D(x * c, y * c)

  def magnitude(): Double = math.sqrt(x*x + y*y)
}

object Vect2D {
  def main(args: Array[String]): Unit = {
    val v1 = new Vect2D(1, 2)
    val v2 = Vect2D(2,2)
    val v3 = v1 + v2
    println(v3.magnitude())
  }

  def apply(x: Double, y: Double): Vect2D = new Vect2D(x, y)
}
