val names = List("Piyush Patel", "Aniket Suthar", "Ishit Patel", "Pragnesh Mohan")

for {name <- names
     upperCaseName = name.toUpperCase
} println(upperCaseName)