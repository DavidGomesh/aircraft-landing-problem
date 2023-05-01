package pj.domain.schedule

import scala.xml.Elem
import pj.domain.*
import pj.domain.DomainError.*
import pj.domain.model.Aircraft
import pj.domain.model.Runway
import pj.domain.simpleTypes.*

object ScheduleMS01 extends Schedule:

    // TODO: Create the code to implement a functional domain model for schedule creation
    //       Use the xml.XML code to handle the xml elements
    //       Refer to https://github.com/scala/scala-xml/wiki/XML-Processing for xml creation
    def create(xml: Elem): Result[Elem] = ???

    // Schedule Aircrafts in Runways
    def schedule(unscheduled: List[Aircraft], runways: List[Runway]): Result[List[Runway]] =
        unscheduled match
            case aircraft :: tail => {
                
                if(Runway.isCompatible(aircraft, runways))

                    val compatibleRunways = runways.filter(r => r.isCompatible(aircraft))
                    val runwaysWithDelays = compatibleRunways.map(runway => (delay(aircraft, runway), runway)).sortBy(_._1)
                    val emergencies = unscheduled.filter(a => a != aircraft && a.emergency.isDefined)

                    if(emergencies.isEmpty)
                        scheduleWithoutEmergency(aircraft, runwaysWithDelays) match
                            case Right(updatedRunway) => {
                                val updatedRunways = updateRunways(updatedRunway, runways)
                                schedule(tail, updatedRunways)
                            }
                            case Left(error: DomainError) => Left(error)
                    else
                        val emergency = emergencies(0)
                        scheduleWithEmergency(aircraft, emergency, runwaysWithDelays) match
                            case Right(updatedRunway) => {
                                val updatedRunways = updateRunways(updatedRunway, runways)
                                schedule(tail, updatedRunways)
                            }
                            case Left(error: DomainError) => {
                                val updatedUnscheduled = advanceAircraft(emergency, unscheduled)
                                schedule(updatedUnscheduled, runways)
                            }
                else
                    Left(AnError("No compatible runways"))
            }
            case Nil => Right(runways)

    // Schedule an aircraft without emergencies
    def scheduleWithoutEmergency(aircraft: Aircraft, runways: List[(Int, Runway)]): Result[Runway] =
        runways match
            case (delay, runway) :: _ => {
                val time = aircraft.target + delay
                runway.insert(aircraft, time) match
                    case Right(runway) => Right(runway)
                    case Left(error: DomainError) => Left(error)
            }
            case _ => Left(AnError("No runways available"))

    // Schedule an aircraft with emergencies
    def scheduleWithEmergency(aircraft: Aircraft, emergency: Aircraft, runways: List[(Int, Runway)]): Result[Runway] =
        runways match
            case (delay, runway) :: tail => {

                runway.insert(aircraft, aircraft.target + delay) match
                    case Right(runway) => {
                        val canBeScheduled = checkEmergency(emergency, List(runway) ::: tail.map(_._2))
                        if canBeScheduled then Right(runway) 
                        else scheduleWithEmergency(aircraft, emergency, tail)
                    }
                    case Left(error: DomainError) => Left(error)
            }
            case Nil => Left(AllRunwaysUnavailable("All runways unavailable for emergencies"))

    // Calculate delay
    def delay(aircraft: Aircraft, runway: Runway): Int =
        runway.aircrafts.foldLeft(0)((greaterDelay, scheduled) => {
            val time = scheduled.time + scheduled.gap(aircraft)
            val delay = time - aircraft.target
            if delay > greaterDelay then delay else greaterDelay
        })

    // Check if an aircraft in emergency can be scheduled
    def checkEmergency(emergency: Aircraft, runways: List[Runway]): Boolean =
        runways.foldLeft(false)((canBeScheduled, runway) => {
            val emerDelay = delay(emergency, runway)
            val maxDelay = emergency.emergency match {
                case Some(value) => value
                case None => PositiveInteger.zero
            }
            if canBeScheduled || emerDelay <= maxDelay then true else false
        })

    // Update the runways list
    def updateRunways(updated: Runway, runways: List[Runway]): List[Runway] =
        val index = runways.indexWhere(_.id == updated.id)
        runways.updated(index, updated)

    // Advance aircraft to start
    def advanceAircraft(emergency: Aircraft, unscheduled: List[Aircraft]): List[Aircraft] =
        val index = unscheduled.indexOf(emergency)
        unscheduled.splitAt(index) match
            case (unscheduled, head :: tail) => {
                List(head) ::: unscheduled ::: tail
            }
            case _ => List()