import scala.annotation.tailrec

// Ensure implementation is tail recursive
// Define continue function with two parameters.
// First takes a single, by-name parameter that is conditional
// Second list takes a single by-name value that is the body to be evaluated for each iteration
@tailrec
def continue(conditional: => Boolean) (body: => Unit): Unit = {
  // If true, evaluate the body and then call the continue recursively
  if(conditional) {
    body
    continue(conditional)(body)
  }
}

var count = 0
continue(count < 5) {
  println(s"at $count")
  count += 1
}