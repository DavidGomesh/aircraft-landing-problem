package pj.domain.schedule

import scala.xml.Elem
import pj.domain.*
import pj.domain.DomainError.*
import pj.domain.model.Aircraft
import pj.domain.model.Runway
import pj.domain.simpleTypes.*

import scala.xml.*
import pj.domain.simpleTypes.*
import pj.domain.schedule.ScheduleMS01
import pj.domain.model.Agenda
import pj.io.AgendaIO

object ScheduleMS01 extends Schedule:

    // TODO: Create the code to implement a functional domain model for schedule creation
    //       Use the xml.XML code to handle the xml elements
    //       Refer to https://github.com/scala/scala-xml/wiki/XML-Processing for xml creation
    def create(xml: Elem): Result[Elem] =

        // val filePath = "files/assessment/ms01/valid00_in.xml"
        final case class Response(id: String, runway: String, time: String)

        AgendaIO.xmlToAgenda(xml) match
            case Right(agenda) => {
                val response = ScheduleMS01.schedule(agenda.aircrafts, agenda.runways, 0)
                response match
                    case Right(runways) => {
                        val resp = runways._2.flatMap(runway => runway.aircrafts.map(aircraft => {
                            Response(aircraft.id.to, runway.id.to, aircraft.time.toString)
                        })).sortBy(_.time.toInt)

                        Right(
                            <schedule cost={runways._1.toString} xsi:schemaLocation="http://www.dei.isep.ipp.pt/tap-2023 ../../schedule.xsd " xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.dei.isep.ipp.pt/tap-2023">
                                {resp.map(item => <aircraft id={item.id} runway={item.runway} time={item.time}/>)}
                            </schedule>
                        )
                        
                    }
                    case Left(error) => Left(error)
            }
            case Left(error) => Left(error)


    // Schedule Aircrafts in Runways
    def schedule(unscheduled: List[Aircraft], runways: List[Runway], cost: Int): Result[(Int, List[Runway])] =
        unscheduled match
            case aircraft :: tail => {
                
                if(Runway.isCompatible(aircraft, runways))

                    val compatibleRunways = runways.filter(r => r.isCompatible(aircraft))
                    val runwaysWithDelays = compatibleRunways.map(runway => (delay(aircraft, runway), runway)).sortBy(_._1)
                    val emergencies = unscheduled.filter(a => a != aircraft && a.emergency.isDefined)

                    if(emergencies.isEmpty)
                        scheduleWithoutEmergency(aircraft, runwaysWithDelays) match
                            case Right(updatedRunway) => {
                                val newCost = calculateCost(aircraft, updatedRunway, runwaysWithDelays)
                                val updatedRunways = updateRunways(updatedRunway, runways)
                                schedule(tail, updatedRunways, cost + newCost)
                            }
                            case Left(error: DomainError) => Left(error)
                    else
                        val emergency = emergencies(0)
                        scheduleWithEmergency(aircraft, emergency, runwaysWithDelays) match
                            case Right(updatedRunway) => {
                                val newCost = calculateCost(aircraft, updatedRunway, runwaysWithDelays)
                                val updatedRunways = updateRunways(updatedRunway, runways)
                                schedule(tail, updatedRunways, cost + newCost)
                            }
                            case Left(error: DomainError) => {
                                val updatedUnscheduled = advanceAircraft(emergency, unscheduled)
                                schedule(updatedUnscheduled, runways, cost)
                            }
                else
                    Left(AnError("No compatible runways"))
            }
            case Nil => Right((cost, runways))

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
    
    def calculateCost(aircraft: Aircraft, runway: Runway, runways: List[(Int, Runway)]): Int =
        val index = runways.indexWhere(r => r._2.id == runway.id)
        val delay = runways(index)._1
        if 1 to 3 contains aircraft.classType then delay * 2 else delay
        
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