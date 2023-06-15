package pj.domain
import org.scalatest.funsuite.AnyFunSuite
import enumerate.ClassType.*
import domain.Aircraft

class AircraftTest extends AnyFunSuite:

  val delay = 30
  val time = 150
  val maxTime = 900
  val target = 120
  val cost = 60

  test("delay should calculate the correct delay when time is greater than target") {
    val aircraft = Aircraft("A1", Class1, target, maxTime, Some(time))
    assert(aircraft.delay == delay)
  }

  test("cost should be calculated correctly for Class2") {
    val aircraft = Aircraft("A2", Class2, target, maxTime, Some(time))
    assert(aircraft.cost == cost)
  }

  test("cost should be calculated incorrectly for Class3") {
    val aircraft = Aircraft("A3", Class5, target, maxTime, Some(time))
    assert(aircraft.cost != 11)
  }

  test("getTime should return the correct time") {
    val aircraft = Aircraft("A4", Class4, target, maxTime, Some(time))
    assert(aircraft.getTime == time)
  }

  test("setTime should update the time correctly") {
    val aircraft = Aircraft("A5", Class5, target, maxTime, Some(time))
    val updatedAircraft = aircraft.setTime(time)
    assert(updatedAircraft.getTime == time)
  }


