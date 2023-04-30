package pj.domain.model

import pj.domain.DomainError.*
import pj.domain.simpleTypes.*

final case class Aircraft(
    id: Identifier, 
    classType: ClassType, 
    target: NonNegativeInteger, 
    emergency: Option[PositiveInteger], 
    time: NonNegativeInteger) {

    def gap(trailing: Aircraft): Int = classType.gap(trailing.classType)

    def assignTime(time: NonNegativeInteger): Aircraft = {
        copy(id, classType, target, emergency, time)
    }
}