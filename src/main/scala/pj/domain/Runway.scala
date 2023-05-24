package domain

import simpleTypes.identifier.RunwayId
import simpleTypes.integer.NonNegativeInt
import enumerate.ClassType

final case class Runway(
    id: RunwayId,
    classes: Seq[ClassType],
    aircrafts: Seq[Aircraft]):

    def addAircraft(a: Aircraft): Runway =
        copy(aircrafts = aircrafts.appended(a))
    
    def isCompatible(a: Aircraft): Boolean =
        classes.contains(a.classType)

object Runway:
    def apply(id: RunwayId, classes: Seq[ClassType]): Runway =
        Runway(id, classes, Seq.empty)

    def assign(a: Aircraft, r: Runway, t: NonNegativeInt): Runway = 
        r.addAircraft(a.setTime(t))


