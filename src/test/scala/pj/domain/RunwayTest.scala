package pj.domain

import org.scalatest.funsuite.AnyFunSuite
import enumerate.ClassType.*
import pj.domain.DomainError.*
import domain.Runway
import _root_.simpleTypes.integer.NonNegativeInt
import domain.Aircraft

class RunwayTest extends AnyFunSuite:

  val runwayId1 = "R1"
  val runwayId2 = "R2"
  val aircraftId1 = "A1"
  val aircraftId2 = "A2"
  val target = 10
  val maxTime = 30
  val time = Some(10)

  test("Creating Runway with empty aircrafts should have zero cost") {
    val runway = Runway(runwayId1, List(Class1, Class2, Class3, Class4), List())
    assert(runway.cost == 0)
  }

  test("Adding an aircraft to Runway should increase its cost") {
    val aircraft = Aircraft(aircraftId1, Class2, target, maxTime, time)
    val runway = Runway(runwayId1, List(Class1, Class2, Class3))
    val updatedRunway = runway.addAircraft(aircraft)
    assert(updatedRunway.cost >= runway.cost)
  }

  test(
    "Setting a sequence of aircrafts to Runway should update its aircrafts"
  ) {
    val aircrafts = Seq(
      Aircraft(aircraftId1, Class1, target, maxTime),
      Aircraft(aircraftId2, Class1, target, maxTime)
    )
    val runway = Runway(runwayId1, List(Class1, Class2, Class3))
    val updatedRunway = runway.setAircrafts(aircrafts)
    assert(updatedRunway.aircrafts == aircrafts)
  }

  test("Runway is compatible with an aircraft") {
    val a = Aircraft(aircraftId1, Class4, target, maxTime, time)
    val runway = Runway(runwayId1, List(Class1, Class2, Class4), List())

    assert(runway.isCompatible(a))
  }

  test(
    "Checking compatibility between an aircraft and Runway should return a Result[Boolean]"
  ) {
    val aircraft = Aircraft(aircraftId1, Class3, target, maxTime)
    val incompatibleRunway = Runway(runwayId1, List(Class2))
    val compatibleRunway = Runway(runwayId2, List(Class1, Class3))

    val result1 = Runway.canBeScheduled(aircraft, incompatibleRunway)
    assert(result1 == Left(RunwayNotCompatible("Runway isn't compatible!")))

    val result2 = Runway.canBeScheduled(aircraft, compatibleRunway)
    assert(result2 == Right(true))
  }

  test(
    "Assigning a single aircraft to a Runway should return a Result[Runway]"
  ) {
    val aircraft = Aircraft(aircraftId1, Class1, target, maxTime, time)
    val runway = Runway(runwayId1, List(Class1, Class3))
    val assignedRunway = Runway.assign(aircraft, runway)
    val expectedRunway = runway.addAircraft(aircraft)
    assert(assignedRunway == Right(expectedRunway))
  }

  test(
    "Updating a Runway in a sequence of Runways should return the updated sequence"
  ) {
    val runway1 = Runway(runwayId1, List(Class1, Class2))
    val runway2 = Runway(runwayId2, List(Class2, Class4))
    val runways = Seq(runway1, runway2)

    val updatedRunway = runway2.copy(classes = List(Class1, Class2, Class4))
    val updatedRunways = Runway.update(runways, updatedRunway)

    assert(updatedRunways == Seq(runway1, updatedRunway))
  }
