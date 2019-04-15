package basics.shapes.shapes

import akka.actor.Actor
import basics.shapes.shapes.Messages.{Exit, Finished, Response}

object Messages {
  object Exit
  object Finished
  case class Response(message: String)
}

class ShapesDrawingActor extends Actor {

  def receive = {
    // If the message is Shape instance, it will draw shape
    case s: Shape =>
      s.draw(str => println(s"ShapesDrawingActor: $str"))
      // send message to sender.
      sender ! Response(s"ShapesDrawingActor: $s drawn")
      // If message is Exit, then process will exit.
    case Exit =>
      println(s"ShapesDrawingActor: Exiting...")
      sender ! Finished
      //If unknown message, throw error.
      // error expression should be last as it matches all objects.
    case unexpected =>
      val response = Response(s"ERROR: Unknown messages: $unexpected")
      println(s"ShapesDrawingActor: $response")
      sender ! response
  }
}
