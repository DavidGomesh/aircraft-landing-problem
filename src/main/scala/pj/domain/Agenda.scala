package domain

import simpleTypes.integer.NonNegativeInt

final case class Agenda(
    aircrafts: Seq[Aircraft],
    runways: Seq[Runway],
    maxDelayTime: NonNegativeInt
)