package simpleTypes.integer

import error.Result
import error.DomainError.*
import scala.annotation.targetName

type NonNegativeInt = Integer

object NonNegativeInt:
    def apply(n: Int): Result[NonNegativeInt] =
        if n > 0 then Right(n) 
        else Left(NonNegativeIntError("Number must be greater then zero"))