for {
  x <- Seq(List(5.5, 5.6, 5.7), List("a", "b"))
} yield (x match {
    // due to java's type erasure, at runtime compiler doesn't know
    // whether List is a List of double or string. So, it says second case statement is unreachable
    // and it print seq double for both inputs
  case seqd: Seq[Double] => ("seq double", seqd)
  case seqs: Seq[String] => ("seq string", seqs)
  case _ => ("unknown", x)
})