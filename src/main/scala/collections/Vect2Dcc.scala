package collections

case class Vect2Dcc (x: Double, y: Double) {

  def +(v: Vect2Dcc): Vect2Dcc = {
    new Vect2Dcc(x + v.x, y + v.y)
  }

  def -(v: Vect2Dcc) = new Vect2Dcc(x - v.x, y - v.y)

  def *(c: Double) = new Vect2Dcc(x * c, y * c)

  def magnitude(): Double = math.sqrt(x*x + y*y)
}

object Vect2Dcc {
  def main(args: Array[String]): Unit = {
    val v1 = Vect2Dcc(1,2)
    val v2 = Vect2Dcc(2,2)
    val v3 = v1 + v2
    println(v3)
    v3.copy(y=99)
  }
}