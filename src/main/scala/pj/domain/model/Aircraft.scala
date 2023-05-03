package pj.domain.model

import pj.domain.DomainError.*
import pj.domain.simpleTypes.*

final case class Aircraft(
    id: Identifier,
    classType: ClassType,
    target: NonNegativeInteger,
    emergency: Option[PositiveInteger],
    time: NonNegativeInteger):

    def gap(trailing: Aircraft): Int = classType.gap(trailing.classType)

    def assignTime(time: NonNegativeInteger): Aircraft =
        copy(id, classType, target, emergency, time)

    def delay(runway: Runway) =
        runway.aircrafts.foldLeft(0)((greaterDelay, scheduled) => {
            val time = scheduled.time + scheduled.gap(this)
            val delay = time - target
            if delay > greaterDelay then delay else greaterDelay
        })