package pj.domain.simpleTypes

import pj.domain.Result
import pj.domain.DomainError.IllegalArgumentError
import scala.annotation.targetName

opaque type NonNegativeInteger = Int

object NonNegativeInteger:
    def apply(number: Int): Result[NonNegativeInteger] =
        if (number >= 0) Right(number)
        else Left(IllegalArgumentError("Invalid non negative integer"))
    def zero: NonNegativeInteger = 0

extension (number: NonNegativeInteger)
    @targetName("NonNegativeInteger.toInt")
    def toInt: Int = number
    @targetName("NonNegativeInteger.sum")
    def +(other: NonNegativeInteger): NonNegativeInteger = number + other
    @targetName("NonNegativeInteger.sub")
    def -(other: NonNegativeInteger): NonNegativeInteger = number - other
    @targetName("NonNegativeInteger.greaterThan")
    def >(other: NonNegativeInteger): Boolean = number > other
    @targetName("NonNegativeInteger.lessOrEqualss")
    def lessOrEquals(other: NonNegativeInteger): Boolean = number <= other
