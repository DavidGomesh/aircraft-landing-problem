package pj.domain

import domain.Runway
import domain.Aircraft
import scala.annotation.tailrec

final case class Plan(runways: Seq[Runway]):
    def cost: Int = Runway.cost(runways)

    def update(r: Runway): Plan =
        val i = runways.indexWhere(old => old.id == r.id)
        copy(runways = runways.updated(i, r))

object Plan:
    
    def createPartitions(aircrafts: Seq[Aircraft]): Seq[Partition] =
        @tailrec
        def loop(la: Seq[Aircraft], lp: Seq[Partition]): Seq[Partition] = la match
            case Nil => lp
            case head +: remaining => {
                val scheduled = lp.lastOption.fold(Seq(head))(_._1 :+ head)
                loop(remaining, lp :+ (scheduled, remaining))
            }
        loop(aircrafts, Seq((Seq.empty, aircrafts)))
