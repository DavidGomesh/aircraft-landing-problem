import domain.Agenda
import domain.Aircraft
import domain.Runway

import enumerate.*
import enumerate.ClassType.*

import pj.domain.DomainError.*
import org.scalatest.funsuite.AnyFunSuite

class AgendaTest extends AnyFunSuite:

  val aircraftId1 = "A1"
  val aircraftId2 = "A2"
  val aircraftId3 = "A3"
  val runwayId1 = "R1"
  val runwayId2 = "R2"
  val runwayId3 = "R3"
  val target = 10
  val maxTime = 900
  val time = Some(5)

  test("Adding aircrafts to an agenda") {
    val aircraft1 = Aircraft(aircraftId1, Class1, target, maxTime, time)
    val aircraft2 = Aircraft(aircraftId2, Class2, target, maxTime, time)
    val agenda = Agenda(Seq.empty, Seq.empty)

    val result = Agenda.addAircrafts(Seq(aircraft1, aircraft2), agenda)

    assert(result == Right(Agenda(Seq(aircraft1, aircraft2), Seq.empty)))
  }

  test("Adding duplicate aircraft to an agenda should result in an error") {
    val aircraft1 = Aircraft(aircraftId1, Class1, target, maxTime, time)
    val agenda = Agenda(Seq(aircraft1), Seq.empty)

    val result = Agenda.addAircraft(aircraft1, agenda)

    assert(result == Left(RepeatedAircraftId(aircraftId1)))
  }

  test("Adding runways to an agenda") {
    val runway1 = Runway(runwayId1, List(Class1, Class2), List())
    val runway2 = Runway(runwayId2, List(Class2, Class3), List())
    val agenda = Agenda(Seq.empty, Seq.empty)

    val result = Agenda.addRunways(Seq(runway1, runway2), agenda)

    assert(result == Right(Agenda(Seq.empty, Seq(runway1, runway2))))
  }

  test("Adding duplicate runway to an agenda should result in an error") {
    val runway1 = Runway(runwayId1, List(Class1, Class2), List())
    val agenda = Agenda(Seq.empty, Seq(runway1))

    val result = Agenda.addRunway(runway1, agenda)

    assert(result == Left(RepeatedRunwayId(runwayId1)))
  }

  test("Creating an agenda from aircrafts and runways") {
    val aircraft1 = Aircraft(aircraftId1, Class1, target, maxTime, time)
    val aircraft2 = Aircraft(aircraftId2, Class2, target, maxTime, time)
    val runway1 = Runway(runwayId1, List(Class1, Class2), List())
    val runway2 = Runway(runwayId2, List(Class2, Class3), List())

    val result = Agenda.from(Seq(aircraft1, aircraft2), Seq(runway1, runway2))

    val expected =
      Right(Agenda(Seq(aircraft1, aircraft2), Seq(runway1, runway2)))
    assert(result == expected)
  }

  test("Agenda should be created with the given aircrafts, runways") {
    val aircrafts = Seq(
      Aircraft(aircraftId1, Class1, target, maxTime, time),
      Aircraft(aircraftId2, Class2, target, maxTime, time),
      Aircraft(aircraftId3, Class3, target, maxTime, None)
    )

    val runways = Seq(
      Runway(runwayId1, List(Class1, Class2), List()),
      Runway(runwayId2, List(Class2, Class3), List()),
      Runway(runwayId3, List(Class3, Class4), List())
    )

    val agenda = Agenda(aircrafts, runways)

    val runwayClass = agenda.runways.flatMap(_.classes)
    val aircraftClass = aircrafts.map(_.classType)

    assert(runwayClass.exists(c => aircraftClass.contains(c)))

  }

  test("Agenda should return the correct number of aircrafts") {
    val aircrafts = Seq(
      Aircraft(aircraftId1, Class1, target, maxTime, time),
      Aircraft(aircraftId2, Class2, target, maxTime, time),
      Aircraft(aircraftId3, Class3, target, maxTime, time)
    )

    val runways = Seq(
      Runway(runwayId1, List(Class1, Class2), aircrafts),
      Runway(runwayId2, List(Class2, Class3), List()),
      Runway(runwayId3, List(Class3, Class4), List())
    )

    val agenda = Agenda(aircrafts, runways)

    assert(agenda.aircrafts.lengthIs == aircrafts.length)
  }

  test("Agenda should return the correct number of runways") {
    val aircrafts = Seq(
      Aircraft(aircraftId1, Class1, target, maxTime, time),
      Aircraft(aircraftId2, Class2, target, maxTime, time),
      Aircraft(aircraftId3, Class3, target, maxTime, None)
    )

    val runways = Seq(
      Runway(runwayId1, List(Class1, Class2), List()),
      Runway(runwayId2, List(Class2, Class3), List()),
      Runway(runwayId3, List(Class3, Class4), List())
    )

    val agenda = Agenda(aircrafts, runways)

    assert(agenda.runways.lengthIs == runways.length)
  }
