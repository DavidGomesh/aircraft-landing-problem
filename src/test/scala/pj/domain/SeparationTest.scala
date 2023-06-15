package pj.domain

import org.scalatest.funsuite.AnyFunSuite
import enumerate.ClassType.*
import domain.Aircraft
import domain.Runway

class SeparationTest extends AnyFunSuite:

  val aircraftId1 = "A1"
  val aircraftId2 = "A2"
  val aircraftId3 = "A3"
  val runwayId1 = "R1"
  val runwayId2 = "R2"
  val target = 10
  val maxTime = 900
  val time = Some(5)

  test("separation between aircrafts") {
    val aircraft1 = Aircraft(aircraftId1, Class1, target, maxTime)
    val aircraft2 = Aircraft(aircraftId2, Class2, target, maxTime)
    assert(Separation.separation(aircraft1, aircraft2) == 69)
  }

  test("minTime calculation") {
    val runway =
      Runway(
        runwayId1,
        List(Class1, Class2),
        List(Aircraft(aircraftId3, Class1, target, maxTime))
      )
    val aircraft = Aircraft(aircraftId2, Class1, target, maxTime)
    assert(Separation.minTime(aircraft, runway) == 82)
  }

  test("delay calculation") {
    val aircraft1 = Aircraft(aircraftId1, Class1, target, maxTime, time)
    val updateAircraft1 =
      Aircraft(aircraftId2, Class1, target, maxTime, time)
    val runway = Runway(runwayId1, List(Class1, Class2), List(updateAircraft1))
    Separation.delay(aircraft1, runway) == 87
  }
