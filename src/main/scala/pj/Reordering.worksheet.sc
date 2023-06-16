val numbers = List(1, 2, 3)
val multipliers = List(10, 20, 30)

val result = numbers.map { (x: Int, multiplier: Int) =>
  x * multiplier
}