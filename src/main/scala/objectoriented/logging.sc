trait Logging {
  def info(message: String): Unit
  def warning(message: String): Unit
  def error(message: String): Unit
}

trait StdoutLogging extends Logging {
  def info(message: String) = println(s"INFO: $message")
  def warning(message: String): Unit = println(s"WARNING: $message")
  def error(message: String): Unit = println(s"ERROR: $message")
}

class ServiceImportante(name: String) {
  def work(i: Int): Int = {
    println(s"ServiceImportante: Doing important work! $i")
    i + 1
  }
}

// mixin logging for stdout in Service class
val service2 = new ServiceImportante("dos") with StdoutLogging {
  // override work method in parent class
  override def work(i: Int): Int = {
    info(s"Staring work: i = $i")
    val result = super.work(i)
    info(s"Ending work: i = $i, result = $result")
    result
  }
}

(1 to 3) foreach (i => println(s"Result: ${service2.work(i)}"))