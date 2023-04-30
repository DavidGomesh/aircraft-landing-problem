package pj.domain.simpleTypes

import pj.domain.Result
import pj.domain.DomainError.IllegalArgumentError
import scala.annotation.targetName

opaque type ClassType = Byte

object ClassType:
    def apply(classType: Byte): Result[ClassType] =
        if (Array.range(1, 7) contains classType) Right(classType)
        else Left(IllegalArgumentError("Invalid class type"))

extension (classType: ClassType)
    @targetName("ClassType.gap")
    def gap(trailing: ClassType): Int =
        val times = Array(
            Array(82,  69,  60,  75, 75, 75),
            Array(131, 69,  60,  75, 75, 75),
            Array(196, 157, 96,  75, 75, 75),
            Array(60,  60,  60,  60, 60, 60),
            Array(60,  60,  60,  60, 60, 60),
            Array(60,  60,  60,  120,120,90),
        )
        times(classType - 1)(trailing - 1)
