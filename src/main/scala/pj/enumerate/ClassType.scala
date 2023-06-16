package enumerate

import ClassType.*
import simpleTypes.integer.NonNegativeInt
import pj.domain.Result
import pj.domain.DomainError.*

enum ClassType:
    case Class1, Class2, Class3, Class4, Class5, Class6
    
object ClassType:
    def LANDING_PENALTY = 2
    def TAKEOFF_PENALTY = 1

    def apply(s: String): ClassType = toClass(s)

    def cost(c: ClassType, delay: NonNegativeInt): Int = c match
        case Class1 | Class2 | Class3 => delay * LANDING_PENALTY
        case Class4 | Class5 | Class6 => delay * TAKEOFF_PENALTY
    
    def toClass(s: String): ClassType = s match
        case "1" => Class1
        case "2" => Class2
        case "3" => Class3
        case "4" => Class4
        case "5" => Class5
        case "6" => Class6
    
    def getClassNum(c: ClassType): String = c.toString.replace("Class", "")
