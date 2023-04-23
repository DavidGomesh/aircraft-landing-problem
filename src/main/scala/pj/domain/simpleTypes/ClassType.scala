package pj.domain.simpleTypes

import pj.domain.Result
import pj.domain.DomainError.IllegalArgumentError

opaque type ClassType = Byte

object ClassType:
    def apply(classType: Byte): Result[ClassType] =
        if (1 to 6 contains classType) Right(classType) 
        else Left(IllegalArgumentError("Invalid class type"))
