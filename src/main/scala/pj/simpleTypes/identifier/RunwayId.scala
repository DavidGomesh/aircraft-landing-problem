package simpleTypes.identifier

import error.Result
import scala.annotation.targetName

type RunwayId = String

object RunwayId:
    def apply(id: String): Result[RunwayId] = Right(id)