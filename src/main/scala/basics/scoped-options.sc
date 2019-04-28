val names = List(Some("Piyush Patel"), None, Some("Aniket Suthar"), Some("Ishit Patel"), None, Some( "Pragnesh Mohan"))

println("First pass: ")
for {
  nameOption <- names
  name <- nameOption
  upperCasedNames = name.toUpperCase()
} println(upperCasedNames)

println("Second option: ")
for {
  // only succeeds when name is a Some
  Some(name) <- names
  upperCasedName = name.toUpperCase()
} println(upperCasedName)