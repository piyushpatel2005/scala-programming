val names = List("Piyush Patel", "Aniket Suthar", "Ishit Patel", "Pragnesh Mohan")

for (name <- names
  if name.contains("Patel")
  if !name.startsWith("Piyush")
) println(name)


for(name <- names
  if name.contains("Patel") && !name.startsWith("Piyush")
) println(name)