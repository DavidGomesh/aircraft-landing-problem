package domain

import simpleTypes.identifier.RunwayId
import enumerate.ClassType

final case class Runway(
    id: RunwayId,
    classes: Seq[ClassType],
    aircrafts: Seq[Aircraft]):

    def addAircraft(a: Aircraft): Runway =
        copy(aircrafts = aircrafts.appended(a))

object Runway:
    def apply(id: RunwayId, classes: Seq[ClassType]): Runway =
        Runway(id, classes, Seq.empty)

    def isCompatible(r: Runway, a: Aircraft): Boolean =
        r.classes.contains(a.classType)

