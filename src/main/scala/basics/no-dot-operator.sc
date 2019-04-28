def isEven(n: Int) = (n % 2) == 0

List(1,2,3,4) filter isEven foreach println

List(1,2,3,4).filter((i: Int) => isEven(i)).foreach((i:Int) => println(i))