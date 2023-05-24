package domain

import simpleTypes.identifier.RunwayId
import enumerate.ClassType

final case class Runway(
    id: RunwayId,
    classes: Seq[ClassType],
    aircrafts: Seq[Aircraft]
)

object Runway:
    def apply(id: RunwayId, classes: Seq[ClassType]): Runway =
        Runway(id, classes, Seq.empty)

extension (r: Runway)
    def isCompatible(a: Aircraft): Boolean = 
        r.classes.contains(a.classType)

    def addAircraft(a: Aircraft): Runway =
        r.copy(aircrafts = r.aircrafts.appended(a))