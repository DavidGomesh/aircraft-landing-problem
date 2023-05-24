package simpleTypes.identifier

import pj.domain.Result
import scala.annotation.targetName

type RunwayId = String

object RunwayId:
    def apply(id: String): Result[RunwayId] = Right(id)