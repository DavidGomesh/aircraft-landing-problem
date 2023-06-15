package domain

import simpleTypes.integer.NonNegativeInt
import pj.domain.Result
import pj.domain.DomainError.*

final case class Agenda(
    aircrafts: Seq[Aircraft],
    runways: Seq[Runway]
)

object Agenda:
    
    def from(la: Seq[Aircraft], lr: Seq[Runway]): Result[Agenda] =
        for
            a <- addAircrafts(la, Agenda(Seq.empty, Seq.empty))
            a <- addRunways(lr, a)
        yield a

    def addAircrafts(la: Seq[Aircraft], ag: Agenda): Result[Agenda] =
        la.foldLeft(Right(ag): Result[Agenda])((ag, ar) =>
            ag.flatMap(addAircraft(ar, _))
        )

    def addAircraft(ar: Aircraft, ag: Agenda): Result[Agenda] =
        ag.aircrafts.exists(ar.id == _.id) match
            case true => Left(RepeatedAircraftId(ar.id))
            case false => Right(ag.copy(aircrafts = ag.aircrafts.appended(ar)))

    def addRunways(lr: Seq[Runway], ag: Agenda): Result[Agenda] =
        lr.foldLeft(Right(ag): Result[Agenda])((ag, r) =>
            ag.flatMap(addRunway(r, _))
        )

    def addRunway(r: Runway, a: Agenda): Result[Agenda] =
        a.runways.exists(r.id == _.id) match
            case true => Left(RepeatedRunwayId(r.id))
            case false => Right(a.copy(runways = a.runways.appended(r)))
