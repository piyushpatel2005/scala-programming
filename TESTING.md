# Testing in Scala

Testing requires a maven build structure. With SBT if you need extra repository, you can include a line like below.

```scala
resolvers += "Codehaus stable repository" at "http://repository.codehaus.org/"
```

Dependencies by default will be located in `.ivy2/cache` directory in home directory.

We can run `sbt clean compile` or can pass task `sbt clean compile "test-only testing.AlbumTest"`

To test certain classes interactively,

```shell
sbt
test-only testing.AlbumTest
```

## ScalaTest
`ShouldMatchers` trait provides a DSL used to make the assertions.
ScalaTest includes matchers that aid in making assertions about strings.

```scala
// String matchers
val string =
  """I fell into a burning ring of fire.
    |I went down, down, down and the flames went higher
  """.stripMargin
string should startWith("I fell")
string should endWith("higher")
string should not endWith "My favorite friend, the end"
string should include ("down, down, down")
string should not include ("Great balls of fire")

string should startWith regex ("I.fel+")
string should include regex ("flames?")

string should fullyMatch regex ("""I(.|\n|\S)*higher""")
// Integer matchers
val answerToLife = 42
answerToLife should be < (50)
answerToLife should not be > (50)
answerToLife should be > (3)
// === is used to evaluate whether the right side is equal to the left.
answerToLife should be === (42)
// Float matchers
(0.9 - 0.8) should not be (0.1 plusOrMinus .01)
(0.4 + 0.1) should not be (40.00 plusOrMinus .30)
// Reference matchers
// The == operator evaluates the natural equality for value types and object identity for reference types, not reference equality.
// The === will assert object equality.
// ScalaTest offers theSameInstanceAs
val garthBrooks = new Artist("Garth", "Brooks")
val chrisGaines = garthBrooks
garthBrooks should be theSameInstanceAs (chrisGaines)
val debbieHarry = new Artist("Debbie", "Harry")
garthBrooks should not be theSameInstanceAs(debbieHarry)

// Iterable matchers
List() should be ('empty)
8 :: 6 :: 7 :: 5 :: 3 :: 0 :: 9 :: Nil should contain(7)
// Seq and traversable matchers
(1 to 9) should have length (9)
(20 to 60 by 2) should have size (21)
// Map matchers
val map = Map("Jimmy Page" -> "Led Zeppelin", "Sting" -> "The Police", "Aimee Mann" -> "Til\' Tuesday")
map should contain key ("Sting")
map should contain value ("Led Zeppelin")
map should not contain key("Brian May")
// Compound matchers
val redHotChilliPeppers = List("Anthony Kiedis", "Flea", "Chad Smith", "Josh Klinghoffer")
redHotChilliPeppers should (contain("Anthony Kiedis") and 
    (not contain("John Fresciante")
    or contain("Dave Navarro")))
redHotChilliPeppers should not (contain ("The Edge") or contain ("Kenny G"))
var total = 3
redHotChilliPeppers should not (contain ("The Edge") or contain {total += 6; "Kenny G"})
total should be (9)

gorillaz should (not be (null) and contain ("Damon Albarn"))
// Property matchers
// ScalaTest has a way to assert that an object's properties are valid in one cohesive check
import scala.collection.mutable.WrappedArray
val album = new Album("Blizzard of Ozz", 1980, new Artist("Ozzy", "Osbourne"))
album should have (
  'title("Blizzard of Ozz"),
  'year (1980),
  'artist (new Artist("Ozzy", "Osbourne"))
)

```

Property matchers can be used to reflect on the object's properties and make assertions on those properties. 

```scala
// Collection matchers
import java.util.{List => JList, ArrayList => JArrayList, Map => JMap, HashMap => JHashMap}
val jList: JList[Int] = new JArrayList[Int](20)
jList.add(3); jList.add(6); jList.add(9)
val emptyJList: JList[Int] = new JArrayList[Int]()
emptyJList should be ('empty)
jList should have length(3)
jList should have size (3)
jList should contain(6)
jList shoudl not contain(10)

val backupBands: JMap[String, String] = new JHashMap()
backupBands.put("Joan Jett", "Blackhearts")
backupBands.put("Tom Petty", "Heartbreakers")
backupBands should contain key("Joan Jett")
backupBands should contain value ("Heartbreakers")
backupBands should not contain key("John Lydon")

```

ScalaTest also includes **MustMatchers**. If an assertion is not met, this matcher will throw a `TestFailedException`.

```scala
val list = 2 :: 4 :: 5 :: Nil
list.size must be(3)
val string =
  """I fell into a burning ring of fire.
    |I went down, down, down and the flames went higher
  """.stripMargin
string must startWith regex ("I.fel+")
string must endWith regex ("h.{4}r")

val answerToLife = 42
answerToLife must be < (50)
answerToLife must not be >(50)

val garthBrooks = new Artist("Garth", "Brooks")
val chrisGaines = garthBrooks
val debbieHarry = new Artist("Debbie", "Harry")
garthBrooks must be theSameInstanceAs (chrisGaines)

(0.9 - 0.8) must be (0.1 plusOrMinus .01)
List() must be ('empty)
1 :: 2 :: 3 :: Nil must contain(3)
(1 to 9).toList must have length(9)
(20 to 60 by 2).toList must have size (21)

val map = Map("Jimmy Page" -> "Led Zeppelin", "Sting" -> "The Plice", "Aimee Mann" -> "Til\' Tuesday")
map must contain key ("Sting")
map must contain value ("Led Zeppelin")
map must not contain key("Brian May")

val redHotChiliPeppers = List("Anthony Kiedis", "Flea", "Chad Smith", "Josh Klinghoffer")
redHotChiliPeppers must (contain("Anthony Kiedis") and 
(not contain("John Frusciante")
or contain("Dave Navarro")))

```

There are couple of ways to verify that an expected exception is made and trapped. One way is to put volatile code in intercept block. 

```scala
"An album" should {
  "throw an IllegalArgumentException if there are no acts when created" in {
    intercept[IllegalArgumentException] {
      new Album("The Joy of Listing to nothing", 1980, List())
    }
  }
}
```

Another way to assert that an exception should be thrown is to use either a ShouldMatcher or MustMatcher to assert that the call indeed throws the necessary Exception. This is done using `evaluating` block and either a `must` or `should` clause to check that it produces the expected Exception. We can also check for the message produced.


```scala
val thrownException = evaluating {
  new Album("The Joy of Listing to nothing", 1980, List()) must produce [IllegalArgumentException]
  thrownException.getMessage() must be "An Artist is required"
}
```

### Informers

Informers in ScalaTest are spices, analogous to debug statements, that can be applied anywhere in a test to display information about the test. To apply an informer, add an `info(string)` method anywhere in your test.

```scala
class AlbumSpec extends FunSpec with ShouldMatchers {
  describe("An Album") {
    it("can add an Artist to the album at construction time") {
    val album = new Album("Thriller", 1981, new Artist("Michael", "Jackson"))
    info("Making sure that Michael is indeed the first name of the artist")
    album.acts.head.asInstanceOf[Artist].firstName should be ("Michael")
    }
  }
}
```

Each test trait lets you mark a test as pending. **pending** is a placeholder for tests that have not been defined yet. You need to add `pending` to the test `it` block.

```scala
it("can add an Artist to the album at construction time") {pending}
it("can add opt to not have any artists at construction time") {pending}
it ("can add an Artist to the album at construction time") {
  val album = new Album("Thriller", 1981, new Artist("Michale", "Jackson"))
  pending
}
```

ScalaTest traits let you temporarily disable certain tests. To **ignore** any poor or broken test, replace the `it` keyword with `ignore`.

```scala
ignore("can add a Producer ")
```

**Tagging** categorizes tests so that they can run as part of a group. This is very useful in some conditions

- When some tests are slow and you want to skip them at times.
- Some tests check related functionality and should be run together.
- Categorize tests as unit tests, integration tests, acceptance tests or any other type. All testing traits can be tagged with strings describing the test.

**GivenWhenThen** allows us to test if Given that initial state of a test, following is a When which actions or verbs are to be performed. Finally, Then specifies the results of the tests.

### Specifications in ScalaTest

### FunSpec

```scala
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{Tag, GivenwhenThen, FunSpec}

class AlbumSpecAll extends FunSpec with ShouldMatchers with GivenWhenThen {
  describe("An Album") {
    it("can add an Artist to the album at construction time", Tag("construction")) {
      given("The album Thriller by Michael Jackson")
      val album = new Album("Thriller", 1981, new Artist("Michael", "Jackson"))
      when ("the artist of the album is obtained")
      then("the artist should be an instance of Artist")
      artist.isInstanceOf[Artist] should be (true)
      
      and("the artist's first name and last name should be Michael Jackson")
      artist.firstName should be ("Michael")
      artist.lastName should be ("Jackson")
      info("This is still pending. since there may be more to accomplish in this test")
      pending
    }
    
    ignore("can add a Producer to an album at construction time") {
      // TODO: Add a producer
    }    
  }
}

```

To run the test in SBT, `test-only AlbumSpecAll -- -n construction`

### WordSpec

It is another type of Spec available in ScalaTest. It makes heavy use of the items when, should and can with the ability to combine these words with any means possible. These methods belong to String by use of `implicit` wrapper.

[WordSpec example](src/test/scala/testing/AlbumWordSpec.scala)

### FeatureSpec

is a test that categorizes a test in a set of features. Each feature must have a unique string to describe the desired feature of the software that is being tested.

Options in Scala are a near replacement for null.
[FeatureSpec example](src/test/scala/testing/AlbumFeatureSpec.scala)

### FreeSpec

FreeSpec is a test that is free of any restraint, the developer can craft it however she sees fit. `GivenWhenThen` can be useful in FresSpec just to bring some structure into the test if needed.

[FreeSpec Example](src/test/scala/testing/JukeboxFeeSpec.scala)

Each sttement that doesn't contain any tests within the block terminates with `-{`. If the statement will contain the assertions required for the test, then an `in` keyword is required.

### FlatSpec

For simplicity, there is FlatSpec, a no-nonsense, flat behavior driven design spec meant to just declare the purpose of the test and implement it.

[FlatSpec example](src/test/scala/testing/TrackFlatSpec.scala)

The use of `must`, `should` or `can` has nothing to do with MustMatchers or ShouldMatchers. Those keywords belong to the `FlatSpec` trait.

### JUnitSuite

ScalaTest supports JUnit testing using a JUnitSuite trait. For this, we have to include junit library in the project. For JUnit, there are annotations like `@Before` for setting up objects before each test method and `@After` for tearing down objects after each method.

[JUnit example](src/test/scala/testing/ArtistJUnitSuite.scala)

### TestNGSuite

TestNG is popular Java-based testing framework. It brought ideas to Java testing, including `DataProviders` - which can provide a list of data to a testing method - and groups - which are analogous to tagging in ScalaTest. We need to include `testng` into our libraryDependencies.

In below example, `testTheStringLength` will now become two tests.

[TestNG example](src/test/scala/testing/ArtistTestNGSuite.scala)

In TestNG, tagging is called groups.

```scala
@Test(dataProvider = "provider", groups = Array("word_count_analysis"))
def testTheStringLength(n1: String, n2: java.lang.Integer): Unit = {
  assertEquals(n1.length, n2)
}

```

We can test this group using `~test-only <package.className -- -n word_count_analysis`

## Fixtures

Each test can be using same set of object dependencies or data in each test. Fixtures allow either the same or different dependencies based on the needs of the test and can also allow sharing of testing structures to ensure that certain rules pass regardless of the object being used.

**Anonymous objects** once created can be reused in every test and it will generate a brand-new dependency object upon request. It contains `fixture` method that creates an anonymous object in Scala with the variable `letterFromHome`. Everytime this method is called, a new object is always created. We can also use mutable object like ListBuffer which contains list of albums as below example.

[AlbumFixtureSpec example](src/test/scala/testing/)

[AlbumMutableFixtureSpec example](src/test/scala/testing)

### Fixture Traits

Alternatively, ScalaTest can be used to create a custom Fixture trait in order to ensure that each test gets a unique subject to test. Every trait that is mixed into an object retains its own methods and is not shared. 

[Fixture Trait with Album](src/test/scala/testing/AlbumFixtureTraitSpec.scala)

Using a trait for fixture encapsulates all the fixtures required per test. To make use of the fixture, we need to instantiate trait in each test.


If we need **one instance per test**, we can use trait `OneInstancePerTest`.

[OneInstancePerTest example](src/test/scala/testing/AlbumListOneInstancePerTestFreeSpec.scala)

**BeforeAndAfter** trait allows to control what gets initialized and what gets torn down with a test.

[BeforeAndAfter Fixture](src/test/scala/testing/AlbumBeforeAndAfterFixtureSpec.scala)

## Specs2

Specs2 is a testing framework with a different focus and perspective that's different from ScalaTest. Specs2 has different way of structuring tests as well as DataTable specifications, etc. They are concurrently executed.

For setting up Specs2 in SBT, add following to `build.sbt` file.

```scala
name := "Specs2 testing"

version := "1.0"

scalaVersion := "2.9.2"

resolvers ++= Seq(
"snapshots" at "http://scala-tools.org/repo-snapshots",
"releases" at "http://scala-tools.org/repo-releases"
)

resolvers ++= Seq(
"sonatype-snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
"sonatype-releases" at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= Seq(
"org.scalatest" % "scalatest_2.9.2" % "1.8" % "test" withSources() withJavadoc(),
"joda-time" % "joda-time" % "1.6.2" withSources withJavadoc(),
"junit" % "junit" % "4.10" withSources() withJavadoc(),
"org.testing" % "testing" % "6.1.1" % "test" withSources() withJavadoc(),
"org.specs2" %% "specs2" % "1.12.3" withSources() withJavadoc()
)

```

```scala
class JukeboxUnitSpecs extends Specification {
  "A Jukebox" should {
    """have a play method that returns a copy of the jukebox that selects the first song on the first album as the current track""" in {
      val groupTherapy = new Album(
          .... ...
      )
    }
  }
}
```

[Full example](src/test/scala/testing/JukeboxUnitSpecs.scala)

The Specs2 unit specification starts with a string that describes the class undergoing the test. The description ends with `should` method and starts a block.

### Matchers in Specs2

#### Simple Matchers

