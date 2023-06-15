package pj.domain.schedule

import scala.xml.Elem
import pj.domain.Result
import pj.domain.DomainError.*
import domain.*
import pj.domain.Plan
import domain.Runway.*
import scala.annotation.tailrec
import pj.io.AgendaIO
import pj.io.AgendaIO.xmlToAgenda
import pj.io.ScheduleIO.*

def GROUP_SIZE: Int = 10

type Partition = (Seq[Aircraft], Seq[Aircraft])

object ScheduleMS03 extends Schedule:

    // TODO: Create the code to implement a functional domain model for schedule creation
    //       Use the xml.XML code to handle the xml elements
    //       Refer to https://github.com/scala/scala-xml/wiki/XML-Processing for xml creation  
    def create(xml: Elem): Result[Elem] = 
        for
            a <- xmlToAgenda(xml)
            r <- bruteForce(a)
            p <- planToXml(r)
        yield p
        

    def createGroups(l: Seq[Aircraft]): Seq[Seq[Aircraft]] = l match
        case Nil => Nil
        case _ => {
            val (chunk, remaining) = l.splitAt(GROUP_SIZE)
            chunk +: createGroups(remaining)
        }

    def createPartitions(aircrafts: Seq[Aircraft]): Seq[Partition] = 
        @tailrec
        def loop(la: Seq[Aircraft], lp: Seq[Partition]): Seq[Partition] = la match 
            case Nil => lp
            case head +: remaining => {
                val scheduled = lp.lastOption.fold(Seq(head))(_._1 :+ head)
                loop(remaining, lp :+ (scheduled, remaining))
            }
        loop(aircrafts, Seq((Seq.empty, aircrafts)))

    def createAllPossibilities(r: Runway, a: Aircraft): Seq[Runway] = 
        createPartitions(r.aircrafts).flatMap { case (la, remaining) => 
            for
                r <- assign(a, r.setAircrafts(la)).toOption
                r <- assign(remaining, r).toOption
            yield r
        }

    def bruteForce_2(la: Seq[Aircraft], lr: Seq[Runway]): Option[Seq[Runway]] = la match
        case Nil => Some(lr)
        case a +: remainingAircrafts => 
            val ls = lr.flatMap (
                runway => assign(a, runway).fold(  
                    
                    error => error match
                        case OperationTimeWindow(error) => 
                            val allPoss = createAllPossibilities(runway, a)
                            allPoss.minByOption(_.cost) match
                                case Some(r) => bruteForce_2(remainingAircrafts, Runway.update(lr, r))
                                case None => Nil
                        case _ => Nil
                    
                    , updatedRunway => 

                    if runway.cost < updatedRunway.cost then
                        val allPoss = createAllPossibilities(runway, a)
                        allPoss.minByOption(_.cost) match
                            case Some(r) => bruteForce_2(remainingAircrafts, Runway.update(lr, r))
                            case None => bruteForce_2(remainingAircrafts, Runway.update(lr, updatedRunway))
                    else
                        bruteForce_2(remainingAircrafts, Runway.update(lr, updatedRunway))
                )
            )
            ls.minByOption(Runway.cost(_))

    def bruteForce(a: Agenda): Result[Plan] = {
        val aircrafts = createGroups(a.aircrafts)
        
        def bruteForceHelper(aircrafts: Seq[Seq[Aircraft]], lr: Seq[Runway]): Result[Seq[Runway]] = aircrafts match
                case Nil => Right(lr)
                case aircraftList +: remainingAircrafts =>
                    bruteForce_2(aircraftList, lr) match
                        case Some(lr) => bruteForceHelper(remainingAircrafts, lr)
                        case None => Left(Error("Deu nao o"))
            
        bruteForceHelper(aircrafts, a.runways).fold(e => Left(e), lr => Right(Plan(lr)))
    }