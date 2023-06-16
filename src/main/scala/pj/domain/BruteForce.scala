package pj.domain

import domain.*
import scala.annotation.tailrec
import domain.Runway.*
import pj.domain.DomainError.*
import pj.domain.PossibilitiesGenerator.*

type Partition = (Seq[Aircraft], Seq[Aircraft])

def GROUP_SIZE: Int = 10

object BruteForce:

    // Generate a better schedule based on an agenda.
    def generatePlan(a: Agenda): Result[Plan] = 
        @tailrec
        def loop(la: Seq[Seq[Aircraft]], lr: Seq[Runway]): Result[Seq[Runway]] = la match
            case Nil => Right(lr)
            case la +: rm => bruteForce(la, lr) match
                case Some(lr) => loop(rm, lr)
                case None => Left(Error("Unexpected error"))
            
        loop(createGroups(a.aircrafts), a.runways).fold
            (Left(_), lr => Right(Plan(lr))
        )

    // Brute force algorithm to generate a better schedule.
    def bruteForce(la: Seq[Aircraft], lr: Seq[Runway]): Option[Seq[Runway]] = la match
        case Nil => Some(lr)
        case a +: remaining => lr.flatMap (r => assign(a, r).fold(
            handleTimeWindowError(_, r, a, lr, remaining),
            createOtherPossibilities(r, _, a, remaining, lr)
        )).minByOption(Runway.cost)

    // Create aircraft groups to execute the brute force algorithm.
    def createGroups(l: Seq[Aircraft]): Seq[Seq[Aircraft]] = l match
        case Nil => Nil
        case _ => {
            val (chunk, remaining) = l.splitAt(GROUP_SIZE)
            chunk +: createGroups(remaining)
        }
    
    // Reallocate an aircraft to another position on a runway in case of a delay that exceeds the time window.
    def handleTimeWindowError(e: DomainError, r: Runway, a: Aircraft, lr: Seq[Runway], remaining: Seq[Aircraft]) = e match
        case OperationTimeWindow(_) =>
            createBetterPossibility(r, a).flatMap(r => bruteForce(remaining, Runway.update(lr, r)))
        case _ => None


    