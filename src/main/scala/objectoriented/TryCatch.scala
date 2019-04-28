package objectoriented

import scala.io.Source
import scala.util.control.NonFatal

object TryCatch {
  def main(args: Array[String]) = {
    args foreach (arg => countLines(arg))
  }

  def countLines(fileName: String) = {
    println()
    var source: Option[Source] = None
    try {
      source = Some(Source.fromFile(fileName))
      val size = source.get.getLines.size
      println(s"file $fileName has $size lines")
    } catch {
      // match only Non fatal exceptions
      case NonFatal(ex) => println(s"Non fatal exception! $ex")
    } finally {
      for (s <- source) {
        println(s"Closing $fileName")
        s.close()
      }
    }
  }
}
