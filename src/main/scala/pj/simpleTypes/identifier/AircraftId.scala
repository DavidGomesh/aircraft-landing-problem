package simpleTypes.identifier

import error.Result
import scala.annotation.targetName

type AircraftId = String

object AircraftId:
    def apply(id: String): Result[AircraftId] = Right(id)