package domain

import simpleTypes.identifier.AircraftId
import enumerate.*
import enumerate.ClassType.*
import simpleTypes.integer.NonNegativeInt
import simpleTypes.integer.PositiveInt
import scala.annotation.targetName

final case class Aircraft(
    id: AircraftId,
    classType: ClassType,
    target: NonNegativeInt,
    maxTime: PositiveInt,
    time: Option[NonNegativeInt]):

    def delay: NonNegativeInt = time.getOrElse(0) - target max 0
    def cost: NonNegativeInt = ClassType.cost(classType, delay)

    def getTime: NonNegativeInt = time.getOrElse(0)
    def setTime(t: NonNegativeInt): Aircraft = copy(time = Some(t))

object Aircraft:
    def apply(id: AircraftId, ct: ClassType, t: NonNegativeInt, e: PositiveInt): Aircraft =
        Aircraft(id, ct, t, e, Option.empty)
    
    
