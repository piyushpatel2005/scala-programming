import scala.util.parsing.json.JSONObject

object Interpolators {
  implicit class jsonForStringContext(val sc: StringContext) {
//    takes variable argument list and constructs JSONObject
    def json(values: Any*): JSONObject = {
      val keyRE = """^[\s{,]*(\S+):\s*""".r
      val keys = sc.parts map {
        case keyRE(key) => key
        case str => str
      }
      // zip the keys and values together
      val kvs = keys zip values
      JSONObject(kvs.toMap)
    }
  }
}

import Interpolators._

val name = "Dean Wampler"
val book = "Programming Scala"
val jsonobj = json"{name: $name, book: $book}"
println(jsonobj)