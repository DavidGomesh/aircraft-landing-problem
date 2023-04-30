package pj.domain.model

import pj.domain.DomainError.*
import pj.domain.simpleTypes.*
import pj.domain.Result

final case class Runway(
    id: Identifier, 
    classes: List[ClassType], 
    aircrafts: List[Aircraft]) {

    def insert(aircraft: Aircraft, time: NonNegativeInteger): Result[Runway] = {
        if(isCompatible(aircraft)){
            val aircrafWithTime = aircraft.assignTime(time)
            Right(copy(id, classes, aircrafts.appended(aircrafWithTime)))
        }
        else Left(Error("Runway isn't compatible"))
    }

    def isCompatible(aircraft: Aircraft): Boolean = {
        classes.contains(aircraft.classType)
    }
}