package pj.domain.model

import pj.domain.simpleTypes.PositiveInteger

final case class Agenda(
    aircrafts: Aircrafts, 
    runways: Runways,
    maximumDelayTime: PositiveInteger
)
