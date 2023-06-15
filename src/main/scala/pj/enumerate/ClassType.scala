package enumerate

import ClassType.*
import simpleTypes.integer.NonNegativeInt

enum ClassType:
    case Class1, Class2, Class3, Class4, Class5, Class6
    
object ClassType:
    def LANDING_PENALTY = 2
    def TAKEOFF_PENALTY = 1

    def cost(c: ClassType, delay: NonNegativeInt): Int = c match
        case Class1 | Class2 | Class3 => delay * LANDING_PENALTY
        case Class4 | Class5 | Class6 => delay * TAKEOFF_PENALTY
