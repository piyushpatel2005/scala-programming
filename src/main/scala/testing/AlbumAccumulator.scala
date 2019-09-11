package testing

import java.util

object AlbumAccumulator {
  def accumulate(map: util.Map[String, Int], tuples: Seq[(String, Int)]) = {
    tuples.foldLeft(map)((a, b) => a + b)
  }
}
