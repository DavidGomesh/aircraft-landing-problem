package pj.domain.properties

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import org.scalatest.run

import pj.domain.Result

import pj.domain.model.Aircraft
import pj.domain.model.Runway

import pj.domain.simpleTypes.*
import pj.domain.simpleTypes.PositiveInteger

import pj.domain.schedule.ScheduleMS01.schedule

import pj.domain.properties.AircraftProperties.*
import pj.domain.properties.RunwaysProperties.genRunway
import pj.domain.properties.AircraftProperties.genAircraft
import pj.domain.properties.AgendaProperties.genAgenda
import pj.domain.properties.RunwaysProperties.genRunways

object ScheduleProperties extends Properties("ScheduleProperties"):

    def verifyRunnway(lr: List[Runway]): Boolean =
        lr.forall(r => verifyAircrafts(r.aircrafts))

    def verifyAircrafts(la: List[Aircraft]): Boolean = 
        la.forall(a => isDiffTime(a, la))

    def isDiffTime(a: Aircraft, la: List[Aircraft]): Boolean =
        la.forall(a2 => a == a2 || a.time != a2.time)

    property("In a valid schedule, every aircraft was assigned a runway") = forAll(genAgenda) {
        (runways, aircrafts) => {
            println(aircrafts)
            val scheduled = schedule(aircrafts, runways, 0)
            scheduled.fold(_ => false, (_, runway) => {
                aircrafts.forall(a => runway.flatMap(_.aircrafts.map(_.id)).contains(a.id))
            })
        }
    }

    property("Each aircraft should be scheduled for a runway which can handle it") = forAll(genAgenda) { 
        (runways, aircrafts) =>{
            val scheduled = schedule(aircrafts, runways, 0)
            scheduled.fold(_ => false, (_, runway) => {
                aircrafts.forall(a => runway.flatMap(_.aircrafts.map(aircraft => 
                    (aircraft.id, aircraft.classType))).contains(a.id, a.classType))
            })
        }
    }

    property("An aircraft can never be scheduled before its “target time”") = forAll(genAgenda) { 
        (runways, aircrafts) =>{
            val scheduled = schedule(aircrafts, runways, 0)
            scheduled.fold(_ => false, (_, runway) => {
                runway.flatMap(_.aircrafts).foldLeft(true)((acc, aircraft) => acc && aircraft.target.lessOrEquals(aircraft.time))
            })
        }
    }

    property("An emergency aircraft should never exceed its allotted time") = forAll(genAgenda) { 
        (runways, aircrafts) =>{
            val scheduled = schedule(aircrafts, runways, 0)
            scheduled.fold(_ => false, (_, runway) => {
                val emergencyAircrafts = runway.flatMap(_.aircrafts)
                    .filter(aircraft => aircraft.emergency.exists(greaterThan(PositiveInteger.zero)))
                    emergencyAircrafts.forall(aircraft => 
                        val timeEmergency = aircraft.emergency.fold(0)(value => value.to)
                        aircraft.time.toInt <= (aircraft.target.toInt + timeEmergency))
            
            })
        }
    }

    property("Schedule the non-emergency aircraft in a 'first come, first served' manner") = forAll(genAgenda) { 
        (runways, aircrafts) =>{
            val scheduled = schedule(aircrafts, runways, 0)
            scheduled.fold(_ => false, (_, runway) => {
                val byTarget = runways.flatMap(_.aircrafts.filter(_.emergency.isEmpty).sortBy(_.target.toInt))
                val byTime = runways.flatMap(_.aircrafts.filter(_.emergency.isEmpty).sortBy(_.time.toInt))
                byTarget == byTime
            })                            
        }
    }
    
    property("Two or more aircraft on a runway should never be assigned simultaneous times.") = forAll(genAgenda) {
        (runways, aircrafts) => {
            val scheduled = schedule(aircrafts, runways, 0)
            scheduled.fold(_ => false, (_, runways) => verifyRunnway(runways))
        }
    }