package domain

import org.scalatest.funsuite.AnyFunSuite
import simpleTypes.integer.{NonNegativeInt, PositiveInt}
import enumerate.ClassType.*
import domain.Aircraft
import domain.Agenda
import domain.Runway

class AgendaTest extends AnyFunSuite:

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
