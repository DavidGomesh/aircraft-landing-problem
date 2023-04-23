package pj.domain.simpleTypes

import pj.domain.Result
import pj.domain.DomainError.IllegalArgumentError

opaque type NonNegativeInteger = Int

object NonNegativeInteger:
    def apply(number: Int): Result[NonNegativeInteger] =
        if (number >= 0) Right(number) 
        else Left(IllegalArgumentError("Invalid non negative integer"))
