import domain.Agenda
import domain.Aircraft
import domain.Runway

import enumerate.*
import enumerate.ClassType.*

import pj.domain.DomainError.*
import org.scalatest.funsuite.AnyFunSuite

class AgendaTest extends AnyFunSuite:

  test("Adding aircrafts to an agenda") {
    val aircraft1 = Aircraft("A1", Class1, 10, 20, Some(5))
    val aircraft2 = Aircraft("A2", Class2, 15, 25, Some(8))
    val agenda = Agenda(Seq.empty, Seq.empty)

    val result = Agenda.addAircrafts(Seq(aircraft1, aircraft2), agenda)

    assert(result == Right(Agenda(Seq(aircraft1, aircraft2), Seq.empty)))
  }

  test("Adding duplicate aircraft to an agenda should result in an error") {
    val aircraft1 = Aircraft("A1", Class1, 10, 20, Some(5))
    val agenda = Agenda(Seq(aircraft1), Seq.empty)

    val result = Agenda.addAircraft(aircraft1, agenda)

    assert(result == Left(RepeatedAircraftId("A1")))
  }

  test("Adding runways to an agenda") {
    val runway1 = Runway("R1", List(Class1, Class2), List())
    val runway2 = Runway("R2", List(Class2, Class3), List())
    val agenda = Agenda(Seq.empty, Seq.empty)

    val result = Agenda.addRunways(Seq(runway1, runway2), agenda)

    assert(result == Right(Agenda(Seq.empty, Seq(runway1, runway2))))
  }

  test("Adding duplicate runway to an agenda should result in an error") {
    val runway1 = Runway("R1", List(Class1, Class2), List())
    val agenda = Agenda(Seq.empty, Seq(runway1))

    val result = Agenda.addRunway(runway1, agenda)

    assert(result == Left(RepeatedRunwayId("R1")))
  }

  test("Creating an agenda from aircrafts and runways") {
    val aircraft1 = Aircraft("A1", Class1, 10, 20, Some(5))
    val aircraft2 = Aircraft("A2", Class2, 15, 25, Some(8))
    val runway1 = Runway("R1", List(Class1, Class2), List())
    val runway2 = Runway("R2", List(Class2, Class3), List())

    val result = Agenda.from(Seq(aircraft1, aircraft2), Seq(runway1, runway2))

    val expected =
      Right(Agenda(Seq(aircraft1, aircraft2), Seq(runway1, runway2)))
    assert(result == expected)
  }

  test("Agenda should be created with the given aircrafts, runways") {
    val aircrafts = Seq(
      Aircraft("A1", Class1, (10), (20), Some(5)),
      Aircraft("A2", Class2, (15), (25), Some(8)),
      Aircraft("A3", Class3, (20), (30), None)
    )

    val runways = Seq(
      Runway("R1", List(Class1, Class2), List()),
      Runway("R2", List(Class2, Class3), List()),
      Runway("R3", List(Class3, Class4), List())
    )

    val agenda = Agenda(aircrafts, runways)

    val runwayClass = agenda.runways.flatMap(_.classes)
    val aircraftClass = aircrafts.map(_.classType)

    assert(runwayClass.exists(c => aircraftClass.contains(c)))

  }

  test("Agenda should return the correct number of aircrafts") {
    val aircrafts = Seq(
      Aircraft("A1", Class1, (10), (20), Some(5)),
      Aircraft("A2", Class2, (15), (25), Some(8)),
      Aircraft("A3", Class3, (20), (30), None)
    )

    val runways = Seq(
      Runway("R1", List(Class1, Class2), aircrafts),
      Runway("R2", List(Class2, Class3), List()),
      Runway("R3", List(Class3, Class4), List())
    )

    val agenda = Agenda(aircrafts, runways)

    assert(agenda.aircrafts.lengthIs == aircrafts.length)
  }

  test("Agenda should return the correct number of runways") {
    val aircrafts = Seq(
      Aircraft("A1", Class1, (10), (20), Some(5)),
      Aircraft("A2", Class2, (15), (25), Some(8)),
      Aircraft("A3", Class3, (20), (30), None)
    )

    val runways = Seq(
      Runway("R1", List(Class1, Class2), List()),
      Runway("R2", List(Class2, Class3), List()),
      Runway("R3", List(Class3, Class4), List())
    )

    val agenda = Agenda(aircrafts, runways)

    assert(agenda.runways.lengthIs == runways.length)
  }
