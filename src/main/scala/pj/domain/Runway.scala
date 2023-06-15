package domain

import simpleTypes.identifier.RunwayId
import simpleTypes.integer.NonNegativeInt
import enumerate.ClassType
import pj.domain.Separation.minTime
import pj.domain.Separation.delay
import pj.domain.Result
import pj.domain.DomainError.*
import scala.annotation.tailrec

final case class Runway(
    id: RunwayId,
    classes: Seq[ClassType],
    aircrafts: Seq[Aircraft]):

    def cost: NonNegativeInt = aircrafts.map(_.cost).sum

    def addAircraft(a: Aircraft): Runway =
        copy(aircrafts = aircrafts.appended(a))

    def setAircrafts(la: Seq[Aircraft]): Runway =
        copy(aircrafts = la)
    
    def isCompatible(a: Aircraft): Boolean =
        classes.contains(a.classType)

object Runway:
    def apply(id: RunwayId, classes: Seq[ClassType]): Runway =
        Runway(id, classes, Seq.empty)

    def assign(a: Aircraft, r: Runway): Result[Runway] = canBeScheduled(a, r).flatMap(_ => 
        Right(r.addAircraft(a.setTime(a.target + delay(a, r)))))

    def assign(la: Seq[Aircraft], r: Runway): Result[Runway] = la match
        case a +: remaining => assign(a, r).fold(Left(_), assign(remaining, _))
        case Nil => Right(r)
    
    def canBeScheduled(a: Aircraft, r: Runway): Result[Boolean] = 
        if !r.isCompatible(a) then Left(RunwayNotCompatible("Runway isn't compatible!")) else
        if minTime(a, r) > a.maxTime then Left(OperationTimeWindow("Out of operation time window!"))
        else Right(true)

    def cost(lr: Seq[Runway]): NonNegativeInt = lr.map(_.cost).sum

    def update(lr: Seq[Runway], r: Runway): Seq[Runway] =
        val i = lr.indexWhere(old => old.id == r.id)
        lr.updated(i, r)

