package testing

import org.joda.time.Period
import org.scalatest.{FlatSpec, MustMatchers}

class TrackFlatSpec extends FlatSpec with MustMatchers {
  behavior of "A Track"

  it should
    """have a constructor that accepts the name and the length of the track in
      |min:sec and returns a jode.time.Period when track.period is called
      |""".stripMargin in {
    val track = new Track("Last Dance", "5:00")
    track.period must be(new Period(0, 5, 0, 0))
  }

  it must
    """throw an IllegalArgumentException with the message "Track name cannot be blank."
  if  the name of the track is blank.""".stripMargin in {
    the[IllegalArgumentException] thrownBy {
      new Track("")
    } must have message ("requirement failed: Track name cannot be blank.")
  }
}
