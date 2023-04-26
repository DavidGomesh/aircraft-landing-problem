package pj.io

import pj.domain.Result
import pj.domain.model.Agenda
import pj.domain.model.Aircraft
import pj.domain.model.Handles
import pj.domain.model.Runway
import pj.domain.simpleTypes.ClassType
import pj.domain.simpleTypes.Identifier
import pj.domain.simpleTypes.NonNegativeInteger
import pj.domain.simpleTypes.PositiveInteger
import pj.xml.XML.fromAttribute
import pj.xml.XML.fromNode
import pj.xml.XML.traverse

import scala.xml.Node

// Agenda
def xmlToAgenda(filePath: String): Result[Agenda] =
    for {
        xml <- FileIO.load(filePath)
        agenda <- xmlToAgenda(xml)
    } yield agenda

// Agenda
def xmlToAgenda(node: Node): Result[Agenda] =
    for {
        aircraftsNode <- fromNode(node, "aircrafts")
        runwaysNode <- fromNode(node, "runways")
        maxDelayTime <- fromAttribute(node, "maximumDelayTime").flatMap(m => PositiveInteger(m.toInt))

        aircrafts <- traverse(aircraftsNode.child.filter(_.label == "aircraft"), xmlToAircraft)
        runways <- traverse(runwaysNode.child.filter(_.label == "runway"), xmlToRunway)

    } yield Agenda(aircrafts, runways, maxDelayTime)

// Aircraft
def xmlToAircraft(aircraft: Node): Result[Aircraft] =
    for {
        id <- fromAttribute(aircraft, "id").flatMap(id => Identifier(id))
        classType <- fromAttribute(aircraft, "class").flatMap(c => ClassType(c.toByte))
        target <- fromAttribute(aircraft, "target").flatMap(t => NonNegativeInteger(t.toInt))

        emergency <- Right(fromAttribute(aircraft, "emergency")
            .flatMap(e => PositiveInteger(e.toInt)).toOption
        )
    } yield Aircraft(id, classType, target, emergency)

// Runnway
def xmlToRunway(runway: Node): Result[Runway] =
    for {
        id <- fromAttribute(runway, "id").flatMap(id => Identifier(id))
        handles <- traverse(runway.child.filter(_.label == "handles"), xmlToHandles)
    } yield Runway(id, handles)

// Handles
def xmlToHandles(handles: Node): Result[Handles] =
    for {
        classType <- fromAttribute(handles, "class").flatMap(c => ClassType(c.toByte))
    } yield Handles(classType)

