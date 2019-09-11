package basics


import scala.util.control.NonFatal
import scala.language.reflectiveCalls

object manage {

  // T will be the type returned by the anonymous function passed in to do work with the resource
  // resource is a by-name parameter.
  // we pass second argument list containing the work to do with the resource, an anonymous function that will take the resource as an argument and return a result of type T
  def apply[R <: {def close() : Unit}, T](resource: => R)(f: R => T) = {
    var res: Option[R] = None
    try {
      // we don't want Source.fromFile(fileName) to be evaluated every time we reference resource
      res = Some(resource) // only reference resource once
      f(res.get)
    } catch {
      case NonFatal(ex) => println(s"Non fatal exception $ex")
    } finally {
      if (res != None) {
        println("Closing resource")
        res.get.close()
      }
    }
  }

}

object TryCatchArm {
  // Usage: scala basics.TryCatch filename1 filename2
  def main(args: Array[String]) = {
    args foreach (arg => countLines(arg))
  }

  import scala.io.Source

  def countLines(filename: String) = {
    println()
    manage(Source.fromFile(filename)) { source =>
      val size = source.getLines.size
      println(s"file $filename has $size lines")
      if (size > 20) throw new RuntimeException("Big file")
    }
  }
}
