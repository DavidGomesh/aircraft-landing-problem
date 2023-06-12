package pj.domain.schedule

import scala.xml.Elem
import pj.domain.*
import pj.domain.DomainError.*
import domain.Aircraft
import domain.Runway
import pj.domain.Separation.delay
import simpleTypes.integer.NonNegativeInt


object ScheduleMS03 extends Schedule:

  // TODO: Create the code to implement a functional domain model for schedule creation
  //       Use the xml.XML code to handle the xml elements
  //       Refer to https://github.com/scala/scala-xml/wiki/XML-Processing for xml creation  
  def create(xml: Elem): Result[Elem] = ???

  def schedule(la: Seq[Aircraft], lr: Seq[Runway]): Result[Seq[Runway]] = la match
    case a +: next => {
      val br = bestRunways(a, lr)
      val sc = scheduleAircraft(a, br)
      val ru = sc.map(r => updateRunways(r, lr))
      ru.map(lr => schedule(next, lr).fold(_ => ???, r=>r))
    }
    case Nil => Right(lr)

  def bestRunways(a: Aircraft, lr: Seq[Runway]) = (lr
    .filter(_.isCompatible(a))
    .map(r => (r, delay(a, r)))
    .sortBy(_._1.aircrafts.length)
    .sortBy(_._2)
  )

  def scheduleAircraft(a: Aircraft, lr: Seq[(Runway, Int)]): Result[Runway] = lr match
    case r +: next => Right(r._1.assign(a, a.target + r._2))
    case Nil => Left(Error("No runways available"))
  
  def updateRunways(r: Runway, lr: Seq[Runway]): Seq[Runway] = 
    val index = lr.indexWhere(_.id == r.id)
    lr.updated(index, r)
  