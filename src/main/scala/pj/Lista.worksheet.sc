

def splitList(l: List[Int], size: Int): List[List[Int]] = l match
    case Nil => Nil
    case _ => {
        val (chunk, remaining) = l.splitAt(size)
        chunk :: splitList(remaining, size)
    }


val inputList = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
val outputList = splitList(inputList, 3)

println(outputList)
