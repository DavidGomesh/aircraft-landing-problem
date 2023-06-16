package domain

import pj.domain.Result
import pj.domain.DomainError.*
import enumerate.ClassType.*
import simpleTypes.identifier.AircraftId

final case class Agenda(
    aircrafts: Seq[Aircraft],
    runways: Seq[Runway]
)

object Agenda:
    
    def from(la: Seq[Aircraft], lr: Seq[Runway]): Result[Agenda] =
        for
            a <- addRunways(lr, Agenda(Seq.empty, Seq.empty))
            a <- addAircrafts(la, a)
        yield a

    def addAircrafts(la: Seq[Aircraft], ag: Agenda): Result[Agenda] =
        findDuplicateId(la) match 
            case Some(id) => Left(RepeatedAircraftId(id))
            case _ => la.foldLeft(Right(ag): Result[Agenda])((ag, ar) =>
                ag.flatMap(addAircraft(ar, _))
            )

    def addAircraft(ar: Aircraft, ag: Agenda): Result[Agenda] =
        if !ag.runways.exists(_.classes.contains(ar.classType)) then 
            Left(NoRunwaysAvailable(getClassNum(ar.classType)))
        else Right(ag.copy(aircrafts = ag.aircrafts.appended(ar)))

    def addRunways(lr: Seq[Runway], ag: Agenda): Result[Agenda] =
        lr.foldLeft(Right(ag): Result[Agenda])((ag, r) =>
            ag.flatMap(addRunway(r, _))
        )

    def addRunway(r: Runway, a: Agenda): Result[Agenda] =
        a.runways.exists(r.id == _.id) match
            case true => Left(RepeatedRunwayId(r.id))
            case false => Right(a.copy(runways = a.runways.appended(r)))

    def findDuplicateId(la: Seq[Aircraft]): Option[AircraftId] =
        def loop(ids: Set[AircraftId], la: Seq[Aircraft]): Option[AircraftId] =
            la match
                case h +: t => if ids.contains(h.id) then Some(h.id) else loop(ids.incl(h.id), t)
                case Nil => Option.empty
        loop(Set(), la)
            

