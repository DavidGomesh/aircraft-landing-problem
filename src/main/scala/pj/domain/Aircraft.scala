package domain

import simpleTypes.identifier.AircraftId
import enumerate.*
import simpleTypes.integer.NonNegativeInt
import simpleTypes.integer.PositiveInt
import scala.annotation.targetName

final case class Aircraft(
    id: AircraftId,
    classType: ClassType,
    target: NonNegativeInt,
    emergency: Option[PositiveInt],
    time: Option[NonNegativeInt]):

    def setTime(t: NonNegativeInt): Aircraft =
        copy(time = Some(t))

object Aircraft:
    def apply(id: AircraftId, ct: ClassType, t: NonNegativeInt, e: PositiveInt): Aircraft =
        Aircraft(id, ct, t, Some(e), Option.empty)

    def apply(id: AircraftId, ct: ClassType, t: NonNegativeInt): Aircraft =
        Aircraft(id, ct, t, None, Option.empty)
