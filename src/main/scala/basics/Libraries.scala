package basics

import java.io.PrintWriter

import scala.io.StdIn._
import scala.io.Source

object Libraries {
  def main(args: Array[String]):Unit = {
//    println("What is your name? ")
//    val name = readLine()
//    println("How old are you? ")
//    val age = readInt()

    // 13 2 24 5
    // 2 23 43 2
    val source = Source.fromFile("matrix.txt")
    val lines = source.getLines()
    val matrix = lines.map(line => line.split(" ").map(_.toDouble)).toArray
    source.close()

    val pw = new PrintWriter("rowsums.txt")
    matrix.foreach(row => pw.println(row.sum))
    pw.close()

    val str = "This is a test"; println(str)
    val filtered = str.filter(_ < 'l'); println(filtered.toString)
    val splitted = str.split(" "); println(splitted.toString)

    val Array(a,b,c) = "one two three".split(" ")
    println(b)
    val lst = buildList()
    println(lst)

    println(concatStrings(lst))

    grade(assignments=List(45,98), quizzes=List(87, 59), tests=List(87))

    val plus3 = add(3)_
    val eight = plus3(5)
  }

  def buildList(): List[String] = {
    println("Enter one string on each line: ")
    val input = readLine()
    if (input == "quit") Nil
    else input :: buildList()
  }

  def concatStrings(words: List[String]): String = {
    if (words.isEmpty) ""
    else words.head + concatStrings(words.tail)
  }

  def concatStringsPattern(words: List[String]): String = words match {
    case Nil => ""
    case h :: t => h + concatStrings(t)
  }

  def grade (quizzes: List[Int]=Nil, assignments: List[Int]=Nil, tests: List[Int]=Nil): Double = {
    ???
  }

  // currying
  def add(x: Int)(y: Int): Int = x + y

//  def threeTuple(a:=> Double): (Double, Double, Double) = {
//    (a,a,a)
//  }
}
