package pj.domain

import enumerate.ClassType
import enumerate.ClassType.*
import domain.Aircraft

object Separation:
    def separation(al: Aircraft, at: Aircraft): Int = 
        val cl = al.classType
        val ct = at.classType
        separation(cl, ct)

    def separation(cl: ClassType, ct: ClassType): Int = (cl, ct) match
        case (Class1, Class1) => 82
        case (Class1, Class2) => 69
        case (Class1, Class3) => 60
        case (Class1, _)      => 75

        case (Class2, Class1) => 131
        case (Class2, Class2) => 69
        case (Class2, Class3) => 60
        case (Class2, _)      => 75

        case (Class3, Class1) => 196
        case (Class3, Class2) => 157
        case (Class3, Class3) => 96
        case (Class3, _)      => 75

        case (Class4, _) => 60
        case (Class5, _) => 60

        case (Class6, Class1 | Class2 | Class3) => 60
        case (Class6, Class4 | Class5) => 120
        case (Class6, _) => 90