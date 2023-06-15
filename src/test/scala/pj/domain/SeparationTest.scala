package pj.domain

import org.scalatest.funsuite.AnyFunSuite
import enumerate.ClassType.*
import domain.Aircraft
import domain.Runway

class SeparationTest extends AnyFunSuite:

  test("separation between aircrafts") {
    val aircraft1 = Aircraft("A1", Class1, 10, 20)
    val aircraft2 = Aircraft("A2", Class2, 20, 40)
    assert(Separation.separation(aircraft1, aircraft2) == 69)
  }

  test("minTime calculation") {
    val runway =
      Runway("R1", List(Class1, Class2), List(Aircraft("A3", Class1, 10, 50)))
    val aircraft = Aircraft("A2", Class1, 30, 100)
    assert(Separation.minTime(aircraft, runway) == 82)
  }

  test("delay calculation") {
    val aircraft1 = Aircraft("A1", Class1, 10, 20, Some(0))
    val updateAircraft1 = Aircraft("A2", Class1, 10, 20, Some(15))
    val runway = Runway("R1", List(Class1, Class2), List(updateAircraft1))
    Separation.delay(aircraft1, runway) == 87
  }
