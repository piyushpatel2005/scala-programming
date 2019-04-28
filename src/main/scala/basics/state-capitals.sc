val stateCapitals = Map(
  "Gujarat" -> "Ahmedabad",
  "Alaska" -> "Juneau",
  "Maharastra" -> "Bombay"
)

println("Get capitals wrapped in Options")
println("Gujarat: " + stateCapitals.get("Gujarat"))
println("Alaska: " + stateCapitals.get("Alaska"))
println("Unknown: " + stateCapitals.get("Unknown"))

println("Get capitals themselves out of the Options: ")
println("Maharastra: " + stateCapitals.get("Maharastra").getOrElse("oops"))
println("Unknown: " + stateCapitals.get("Unknown").getOrElse("oops"))