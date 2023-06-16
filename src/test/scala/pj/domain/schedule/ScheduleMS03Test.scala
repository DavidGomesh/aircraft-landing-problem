package pj.domain.schedule

import scala.language.adhocExtensions
import org.scalatest.funsuite.AnyFunSuite
import enumerate.ClassType.*
import enumerate.ClassType
import domain.Aircraft
import domain.Runway
import domain.Agenda
import pj.domain.Result
import pj.domain.Plan
import pj.domain.Plan.*
import pj.domain.BruteForce.*
import pj.domain.PossibilitiesGenerator.*

import pj.domain.schedule.ScheduleMS03.*

// TODO: Create the code to test a functional domain model for schedule creation.
//       create files in the files/test/ms01 folder
class ScheduleMS03Test extends AnyFunSuite:
  val runwayId1 = "R1"
  val runwayId2 = "R2"
  val runwayId3 = "R3"
  val aircraftId1 = "A1"
  val aircraftId2 = "A2"
  val aircraftId3 = "A3"
  val maxTime = 900
  val target1 = 10
  val target2 = 15
  val target3 = 20

  val aircrafts = Seq[Aircraft](
    Aircraft(aircraftId1, Class2, target1, maxTime, Some(0)),
    Aircraft(aircraftId2, Class1, target2, maxTime, Some(0)),
    Aircraft(aircraftId3, Class3, target3, maxTime, Some(0))
  )

  val runways = Seq[Runway](
    Runway(runwayId1, Seq(Class2, Class5)),
    Runway(runwayId2, Seq(Class1, Class5, Class3))
  )

  val aircrafts2 = Seq[Aircraft](
    Aircraft(aircraftId1, Class2, target1, maxTime, Some(0)),
    Aircraft(aircraftId2, Class1, target2, maxTime, Some(0))
  )

  test("generatePlan should produce the expected plan for a valid agenda") {

    val agenda = Agenda(aircrafts, runways)

    val result = generatePlan(agenda)

    val agendaExpected = Right(
      Plan(
        Seq(
          Runway(
            runwayId1,
            Seq(Class2, Class5),
            Seq(Aircraft(aircraftId1, Class2, target1, maxTime, Some(10)))
          ),
          Runway(
            runwayId2,
            Seq(Class1, Class5, Class3),
            Seq(
              Aircraft(aircraftId2, Class1, target2, maxTime, Some(15)),
              Aircraft(aircraftId3, Class3, target3, maxTime, Some(75))
            )
          )
        )
      )
    )

    assert(result == agendaExpected)
  }

  test("generatePlan should return an error for an invalid agenda") {
    val agenda = Agenda(
      aircrafts,
      Seq(
        Runway(runwayId1, Seq(Class2, Class5)),
        Runway(runwayId2, Seq(Class2, Class5, Class3))
      )
    )
    val result = generatePlan(agenda)

    assert(result.isLeft)
  }

  test("createGroups should divide the aircraft Seq into groups of size 10") {

    val aircraftSeq: Seq[Aircraft] = (1 to 15).toList.map { i =>
      Aircraft(s"aircraftI$i", Class1, i, 900, Some(0))
    }
    val groups = createGroups(aircraftSeq)

    assert(groups.lengthIs == 2)
    assert(groups(0).lengthIs == 10)
    assert(groups(1).lengthIs == 5)
  }

  test("createPartitions should correctly partition the aircrafts") {

    val partitions = createPartitions(aircrafts)

    assert(partitions.lengthIs == 4)

    val expectedPartitions = Seq(
      (
        Seq(),
        Seq(
          Aircraft(aircraftId1, Class2, 10, 900, Some(0)),
          Aircraft(aircraftId2, Class1, 15, 900, Some(0)),
          Aircraft(aircraftId3, Class3, 20, 900, Some(0))
        )
      ),
      (
        Seq(Aircraft(aircraftId1, Class2, 10, 900, Some(0))),
        Seq(
          Aircraft(aircraftId2, Class1, 15, 900, Some(0)),
          Aircraft(aircraftId3, Class3, 20, 900, Some(0))
        )
      ),
      (
        Seq(
          Aircraft(aircraftId1, Class2, 10, 900, Some(0)),
          Aircraft(aircraftId2, Class1, 15, 900, Some(0))
        ),
        Seq(Aircraft(aircraftId3, Class3, 20, 900, Some(0)))
      ),
      (
        Seq(
          Aircraft(aircraftId1, Class2, 10, 900, Some(0)),
          Aircraft(aircraftId2, Class1, 15, 900, Some(0)),
          Aircraft(aircraftId3, Class3, 20, 900, Some(0))
        ),
        Seq()
      )
    )

    assert(partitions == expectedPartitions)
  }

  test("bruteForce should find the optimal solution") {

    val result = bruteForce(aircrafts, runways)

    val resultExpected = Some(
      Seq(
        Runway(
          runwayId1,
          Seq(Class2, Class5),
          Seq(Aircraft(aircraftId1, Class2, 10, 900, Some(10)))
        ),
        Runway(
          runwayId2,
          Seq(Class1, Class5, Class3),
          Seq(
            Aircraft(aircraftId2, Class1, 15, 900, Some(15)),
            Aircraft(aircraftId3, Class3, 20, 900, Some(75))
          )
        )
      )
    )

    assert(result == resultExpected)
  }

  test("createAllPossibilities should generate all possible combinations") {
    val runway =
      Runway(runwayId1, Seq(Class2, Class5, Class3, Class1), aircrafts2)

    val aircraft = Aircraft(aircraftId3, Class3, target3, maxTime, Some(0))

    val expectedCombinations = Seq(
      Runway(
        runwayId1,
        Seq(Class2, Class5, Class3, Class1),
        Seq(
          Aircraft(aircraftId3, Class3, 20, 900, Some(20)),
          Aircraft(aircraftId1, Class2, 10, 900, Some(177)),
          Aircraft(aircraftId2, Class1, 15, 900, Some(308))
        )
      ),
      Runway(
        runwayId1,
        Seq(Class2, Class5, Class3, Class1),
        Seq(
          Aircraft(aircraftId1, Class2, 10, 900, Some(0)),
          Aircraft(aircraftId3, Class3, 20, 900, Some(60)),
          Aircraft(aircraftId2, Class1, 15, 900, Some(256))
        )
      ),
      Runway(
        runwayId1,
        Seq(Class2, Class5, Class3, Class1),
        Seq(
          Aircraft(aircraftId1, Class2, 10, 900, Some(0)),
          Aircraft(aircraftId2, Class1, 15, 900, Some(0)),
          Aircraft(aircraftId3, Class3, 20, 900, Some(60))
        )
      )
    )

    val combinations = generateAllPossibilities(runway, aircraft)

    assert(combinations == expectedCombinations)
  }
