package simpleTypes.integer

import error.Result
import error.DomainError.*
import scala.annotation.targetName

type PositiveInt = Integer

object PositiveInt:
    def apply(n: Int): Result[PositiveInt] =
        if n >= 0 then Right(n) 
        else Left(PositiveIntError("Number must be positive"))