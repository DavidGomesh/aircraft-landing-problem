package pj.domain.model

import pj.domain.DomainError.*
import pj.domain.simpleTypes.*

final case class Aircraft(
    id: Identifier,
    classType: ClassType,
    target: NonNegativeInteger,
    emergency: Option[PositiveInteger],
    time: NonNegativeInteger):

    val isInEmergency: Boolean = emergency.isDefined

    def gap(trailing: Aircraft): Int = classType.gap(trailing.classType)

    def assignTime(time: NonNegativeInteger): Aircraft =
        copy(id, classType, target, emergency, time)

    def isCompatible(runway: Runway): Boolean =
        runway.isCompatible(this)

    def isCompatible(runways: List[Runway]): Boolean =
        runways.foldLeft(false)((isCompatible, runway) => {
            isCompatible || this.isCompatible(runway)
        })

    def delay(runway: Runway) = 
        runway.aircrafts.foldLeft(0)((greaterDelay, scheduled) => {
            val time = scheduled.time + scheduled.gap(this)
            val delay = time - target
            if delay > greaterDelay then delay else greaterDelay
        })
    
    def canBeScheduled(runway: Runway) =
        if(!isCompatible(runway))
            false
        else if(emergency.isDefined)
            val maxDelay = emergency.getOrElse(PositiveInteger.zero)
            maxDelay <= delay(runway)
        else true
        


object Aircraft:
    def hasEmergency(aircrafts: List[Aircraft]): Boolean =
        aircrafts.foldLeft(false)((hasEmergency, aircraft) => {
            hasEmergency || aircraft.isInEmergency
        })

    def getEmergencies(aircrafts: List[Aircraft]): List[Aircraft] =
        aircrafts.filter(_.isInEmergency)

