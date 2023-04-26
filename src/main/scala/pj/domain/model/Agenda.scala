package pj.domain.model

import pj.domain.simpleTypes.PositiveInteger

final case class Agenda(
    aircrafts: List[Aircraft], 
    runways: List[Runway],
    maximumDelayTime: PositiveInteger
)
