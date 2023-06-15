package pj.io

import pj.domain.Result
import domain.Agenda
import scala.xml.Elem
import pj.domain.DomainError.*
import scala.xml.Node
import simpleTypes.integer.NonNegativeInt
import domain.Aircraft
import pj.xml.XML.fromAttribute
import enumerate.ClassType
import domain.Runway
import pj.xml.XML.traverse

object AgendaIO:

    def apply(path: String): Result[Agenda] = load(path)

    def load(path: String) = 
        for
            file <- FileIO.load(path)
            a <- xmlToAgenda(file)
        yield a

    def xmlToAgenda(xml: Node): Result[Agenda] =
        for
            md <- fromAttribute(xml, "maximumDelayTime").map(_.toInt)
            la <- traverse((xml\"aircrafts"\"aircraft"), xmlToAircraft(_, md))
            lr <- traverse((xml\"runways"\"runway"), xmlToRunway(_))
            a <- Agenda.from(la, lr)
        yield a

    def xmlToAircraft(xml: Node, maxDelay: NonNegativeInt): Result[Aircraft] =
        for
            id <- fromAttribute(xml, "id")
            c  <- fromAttribute(xml, "class").map(ClassType(_))
            t  <- fromAttribute(xml, "target").map(_.toInt)
            e  <- fromAttribute(xml, "emergency").fold(
                e => Right(t + maxDelay), e => Right(t + e.toInt)
            )
        yield Aircraft(id, c, t, e)

    def xmlToRunway(xml: Node): Result[Runway] = 
        for
            id <- fromAttribute(xml, "id")
            lc <- traverse((xml\"handles"), h => fromAttribute(h, "class").map(ClassType(_)))
        yield Runway(id, lc)