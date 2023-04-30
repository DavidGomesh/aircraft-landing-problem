package pj.domain.simpleTypes

import pj.domain.Result
import pj.domain.DomainError.IllegalArgumentError
import scala.annotation.targetName

opaque type PositiveInteger = Int

object PositiveInteger:
    def zero: PositiveInteger = 0
    def apply(number: Int): Result[PositiveInteger] = 
        if (number > 0) Right(number) 
        else Left(IllegalArgumentError("Invalid positive integer"))

extension (number: PositiveInteger)
    @targetName("PositiveInteger.lessOrEquals")
    def <=(other: PositiveInteger): Boolean = number <= other
