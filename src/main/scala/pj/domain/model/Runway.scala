package pj.domain.model

import pj.domain.DomainError.*
import pj.domain.simpleTypes.*
import pj.domain.Result

final case class Runway(
    id: Identifier, 
    classes: List[ClassType], 
    aircrafts: List[Aircraft]):

    def insert(aircraft: Aircraft, time: NonNegativeInteger): Result[Runway] =
        if(isCompatible(aircraft))
            val aircraftWithTime = aircraft.assignTime(time)
            Right(copy(id, classes, aircrafts.appended(aircraftWithTime)))
        else Left(AnError("Runway isn't compatible"))

    def isCompatible(aircraft: Aircraft): Boolean =
        classes.contains(aircraft.classType)

object Runway:

    def isCompatible(aircraft: Aircraft, runways: List[Runway]): Boolean =
        runways.foldLeft(false)((isCompatible, runway) => {
            isCompatible || runway.isCompatible(aircraft)
        })
    
    def getCompatible(aircraft: Aircraft, runways: List[Runway]): List[Runway] = 
        runways.filter(_.isCompatible(aircraft))
    
    def calculateDelay(aircraft: Aircraft, runways: List[Runway]): List[(Int, Runway)] = 
        runways.map(runway => (aircraft.delay(runway), runway))
    
    def updateRunways(updated: Runway, runways: List[Runway]): List[Runway] =
        val index = runways.indexWhere(_.id == updated.id)
        runways.updated(index, updated)
