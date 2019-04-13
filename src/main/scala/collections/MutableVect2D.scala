package collections

class MutableVect2D (private[collections] var _x: Double, private var _y: Double) {

  def x = _x
  def y = _y

  def x_(newX: Double): Unit = _x = newX
  def y_(newY: Double): Unit = _y = newY
  def plus(mv: MutableVect2D): MutableVect2D = {
    _x += mv.x
    _y += mv.y
    this
  }

  def minus(mv: MutableVect2D): MutableVect2D = {
    _x -= mv.x
    _y -= mv.y
    this
  }

  def minus(c:Double): MutableVect2D = {
    _x *= c
    _y *= c
    this
  }

  def magnitude = math.sqrt(x * x + y * y)

  def apply(index: Int): Double = index match {
    case 0 => _x
    case 1 => _y
    case _ => throw new IndexOutOfBoundsException(s"2D vector indexed at $index")
  }

  def update(index: Int, newValue: Double): Unit = index match {
    case 0 => _x = newValue
    case 1 => _y = newValue
    case _ => throw new IndexOutOfBoundsException(s"2D vector updated at $index")
  }
}


object MutableVect2D {
  def main(args: Array[String]): Unit = {
    val v1 = MutableVect2D(1,2)
    val v2 = MutableVect2D(2,2)
    v1.plus(v2)
    println(v1.magnitude)
    v1._x = 10

  }

  def apply(x: Double, y: Double) = new MutableVect2D(x, y)
}