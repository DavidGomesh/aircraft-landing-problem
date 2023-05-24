package domain

import enumerate.*
import enumerate.ClassType.*
import simpleTypes.identifier.AircraftId
import simpleTypes.integer.NonNegativeInt
import simpleTypes.integer.PositiveInt
import scala.annotation.targetName

final case class Aircraft(
    id: AircraftId,
    classType: ClassType,
    target: NonNegativeInt,
    emergency: Option[PositiveInt],
    time: Option[NonNegativeInt]
)

object Aircraft:
    def apply(id: AircraftId, ct: ClassType, t: NonNegativeInt, e: PositiveInt): Aircraft =
        Aircraft(id, ct, t, Some(e), Option.empty)

    def apply(id: AircraftId, ct: ClassType, t: NonNegativeInt): Aircraft =
        Aircraft(id, ct, t, None, Option.empty)

extension (a: Aircraft)
    @targetName("Aircraft.separation")
    def separation(t: Aircraft): Int = 
        val cl = a.classType
        val ct = t.classType
        cl.separation(ct)
    
    def setTime(t: NonNegativeInt): Aircraft =
        a.copy(time = Some(t))
