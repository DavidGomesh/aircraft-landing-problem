package pj.domain.simpleTypes

import org.scalatest.funsuite.AnyFunSuite
import enumerate.ClassType
import simpleTypes.identifier.AircraftId
import simpleTypes.identifier.RunwayId
import simpleTypes.integer.NonNegativeInt
import pj.domain.DomainError.*
import simpleTypes.integer.PositiveInt


class SimpleTypes extends AnyFunSuite:

  val aircraftId = "A1"

  //identifier tests
  test("Creating AircraftId with valid id should return Right") {
    
    val result = AircraftId(aircraftId)
    assert(result == Right(aircraftId))
  }

  test("Creating RunwaysId with valid id should return Right") {
    val result = RunwayId(aircraftId)
    assert(result == Right(aircraftId))
  }

  //integer tests
  test("Number not must to be greater than 0") {
    val result = Seq( 2, 9, 8)
    result.foreach { n =>
        val result = NonNegativeInt(n)
        assert(result == Right(n))
    }
  }

  test("NonNegativeInt returns Left for invalid negativeInt values") {
    val invalidInt = Seq( -10, -20, 0)
    invalidInt.foreach { n =>
        val result = NonNegativeInt(n)
        assert(result == Left(NonNegativeIntError("Number must be greater than zero")))
    }
}

  test("Number must to be greater than 0") {
    val result = Seq( 0, 100, 10, 20)
    result.foreach { n =>
        val result = PositiveInt(n)
        assert(result == Right(n))
    }
  }

  test("PositiveInt returns Left for invalid PositiveInt values") {
    val invalid = Seq( -10, -1, -20)
    invalid.foreach { n =>
        val result = PositiveInt(n)
        assert(result == Left(PositiveIntError("Number must be positive")))
    }
}