package pj.domain.simpleTypes

import pj.domain.Result
import pj.domain.DomainError.IllegalArgumentError

opaque type PositiveInteger = Int

object PositiveInteger:
    def apply(number: Int): Result[PositiveInteger] = 
        if (number > 0) Right(number) 
        else Left(IllegalArgumentError("Invalid positive integer"))
