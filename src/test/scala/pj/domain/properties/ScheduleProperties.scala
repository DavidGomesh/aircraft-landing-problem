package pj.domain.properties

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import org.scalatest.run

import pj.domain.Result

import pj.domain.simpleTypes.*
import domain.*
import pj.domain.properties.AgendaProperties.genAgenda
import pj.domain.schedule.ScheduleMS03.*
import pj.domain.BruteForce.*
import pj.domain.Plan.*

object ScheduleProperties extends Properties("ScheduleProperties"):

  def verifyRunnway(lr: Seq[Runway]): Boolean =
    lr.forall(r => verifyAircrafts(r.aircrafts))

  def verifyAircrafts(la: Seq[Aircraft]): Boolean =
    la.forall(a => isDiffTime(a, la))

  def isDiffTime(a: Aircraft, la: Seq[Aircraft]): Boolean =
    la.forall(a2 => a == a2 || a.time != a2.time)

  property("In a valid schedule, every aircraft was assigned a runway") =
    forAll(genAgenda) { (agenda) =>
      {
        val aircrafts = agenda.aircrafts
        val r = agenda.runways
        val scheduled = generatePlan(agenda)
        scheduled.fold(
          _ => false,
          (runway) => {
            aircrafts.forall(air =>
              runway.runways.flatMap(_.aircrafts.map(_.id)).contains(air.id)
            )
          }
        )
      }
    }

  property(
    "Each aircraft should be scheduled for a runway which can handle it"
  ) = forAll(genAgenda) { (agenda) =>
    {
      val aircrafts = agenda.aircrafts
      val scheduled = generatePlan(agenda)
      scheduled.fold(
        _ => false,
        (runway) => {
          aircrafts.forall(a =>
            runway.runways
              .flatMap(
                _.aircrafts.map(aircraft => (aircraft.id, aircraft.classType))
              )
              .contains(a.id, a.classType)
          )
        }
      )
    }
  }

  property("An aircraft can never be scheduled before its target time") =
    forAll(genAgenda) { (agenda) =>
      {
        val aircrafts = agenda.aircrafts
        val scheduled = generatePlan(agenda)
        scheduled.fold(
          _ => false,
          (runway) => {
            runway.runways
              .flatMap(_.aircrafts)
              .foldLeft(true)((acc, aircraft) =>
                acc && (aircraft.target <= aircraft.time.getOrElse(0))
              )
          }
        )
      }
    }

  property(
    "Two or more aircraft on a runway should never be assigned simultaneous times."
  ) = forAll(genAgenda) { (agenda) =>
    {
      val scheduled = generatePlan(agenda)
      scheduled.fold(_ => false, (runway) => verifyRunnway(runway.runways))
    }
  }
