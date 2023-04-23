package pj.domain.model

import pj.domain.simpleTypes.Identifier
import pj.domain.simpleTypes.ClassType
import pj.domain.simpleTypes.NonNegativeInteger
import pj.domain.simpleTypes.PositiveInteger

final case class Aircraft(
    id: Identifier,
    classType: ClassType,
    target: NonNegativeInteger,
    emergency: Option[PositiveInteger]
)
