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

