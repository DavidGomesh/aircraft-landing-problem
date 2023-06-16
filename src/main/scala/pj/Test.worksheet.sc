import pj.io.ScheduleIO
import pj.domain.schedule.ScheduleMS03
import pj.domain.schedule.ScheduleMS03.*
import pj.io.AgendaIO
import domain.Agenda
import pj.domain.DomainError.OperationTimeWindow
import scala.annotation.tailrec
import pj.domain.Plan
import pj.domain.DomainError
import pj.domain.Result
import pj.domain.Separation

import enumerate.ClassType.*
import domain.Aircraft
import domain.Aircraft.*
import domain.Runway
import domain.Runway.*
import simpleTypes.identifier.RunwayId

// " Ola ".trim + "ola"

for
    // a <- AgendaIO.load("files\\assessment\\ms03\\invalid01_in.xml")
    a <- AgendaIO.load("files\\assessment\\ms03\\valid_FRA_01_in.xml")
    r <- ScheduleMS03.generatePlan(a)
    p <- ScheduleIO.planToXml(r)
yield p

// val la = List(
//     Aircraft("Aircraft1", Class5, 0, 0 + 900),
//     Aircraft("Aircraft2", Class5, 45, 45 + 900),
//     Aircraft("Aircraft4", Class6, 57, 57 + 900),
//     Aircraft("Aircraft5", Class5, 87, 87 + 900),
//     Aircraft("Aircraft6", Class6, 118, 118 + 900),
//     Aircraft("Aircraft7", Class4, 132, 132 + 900),
//     Aircraft("Aircraft3", Class4, 168, 168 + 900),
//     Aircraft("Aircraft8", Class1, 174, 174 + 900),
//     Aircraft("Aircraft9", Class2, 221, 221 + 900),
//     Aircraft("Aircraft10", Class6, 250, 250 + 900),
//     Aircraft("Aircraft11", Class5, 311, 311 + 900),
//     Aircraft("Aircraft12", Class3, 385, 385 + 900),
//     Aircraft("Aircraft13", Class6, 422, 422 + 900),
//     Aircraft("Aircraft14", Class3, 440, 440 + 900),
//     Aircraft("Aircraft15", Class5, 443, 443 + 900),
//     Aircraft("Aircraft16", Class2, 471, 471 + 900),
//     Aircraft("Aircraft17", Class2, 535, 535 + 900),
//     Aircraft("Aircraft18", Class5, 562, 562 + 900),
//     Aircraft("Aircraft19", Class6, 616, 616 + 900),
//     Aircraft("Aircraft20", Class2, 655, 655 + 900),
//     Aircraft("Aircraft21", Class2, 689, 689 + 900),
//     Aircraft("Aircraft22", Class6, 709, 709 + 900),
//     Aircraft("Aircraft23", Class3, 712, 712 + 900),
//     Aircraft("Aircraft24", Class2, 728, 728 + 900),
//     Aircraft("Aircraft25", Class3, 739, 739 + 900),
//     Aircraft("Aircraft26", Class2, 743, 743 + 900),
//     Aircraft("Aircraft27", Class6, 758, 758 + 900),
//     Aircraft("Aircraft28", Class1, 762, 762 + 900),
//     Aircraft("Aircraft29", Class3, 779, 779 + 900),
//     Aircraft("Aircraft30", Class6, 822, 822 + 900),
//     Aircraft("Aircraft31", Class4, 909, 909 + 900),
//     Aircraft("Aircraft32", Class3, 917, 917 + 900),
//     Aircraft("Aircraft33", Class5, 929, 929 + 900),
//     Aircraft("Aircraft34", Class2, 952, 952 + 900),
//     Aircraft("Aircraft35", Class3, 954, 954 + 900),
//     Aircraft("Aircraft36", Class2, 975, 975 + 900),
//     Aircraft("Aircraft37", Class3, 1000, 1000 + 900),
//     Aircraft("Aircraft38", Class5, 1009, 1009 + 900),
//     Aircraft("Aircraft39", Class5, 1078, 1078 + 900),
//     Aircraft("Aircraft40", Class3, 1098, 1098 + 900),
//     Aircraft("Aircraft41", Class4, 1211, 1211 + 900),
//     Aircraft("Aircraft42", Class6, 1219, 1219 + 900),
//     Aircraft("Aircraft43", Class2, 1234, 1234 + 900),
//     Aircraft("Aircraft44", Class5, 1283, 1283 + 900),
//     Aircraft("Aircraft45", Class4, 1307, 1307 + 900),
//     Aircraft("Aircraft46", Class3, 1314, 1314 + 900),
//     Aircraft("Aircraft47", Class2, 1411, 1411 + 900),
//     Aircraft("Aircraft48", Class3, 1491, 1491 + 900),
//     Aircraft("Aircraft49", Class5, 1513, 1513 + 900),
//     Aircraft("Aircraft50", Class6, 1520, 1520 + 900),
//     Aircraft("Aircraft51", Class1, 1530, 1530 + 900),
//     Aircraft("Aircraft52", Class1, 1587, 1587 + 900),
//     Aircraft("Aircraft53", Class4, 1600, 1600 + 900),
//     Aircraft("Aircraft54", Class6, 1610, 1610 + 900),
//     Aircraft("Aircraft55", Class2, 1629, 1629 + 900),
//     Aircraft("Aircraft56", Class3, 1635, 1635 + 900),
//     Aircraft("Aircraft57", Class6, 1668, 1668 + 900),
//     Aircraft("Aircraft58", Class1, 1673, 1673 + 900),
//     Aircraft("Aircraft59", Class1, 1714, 1714 + 900),
//     Aircraft("Aircraft60", Class5, 1766, 1766 + 900),
// )

// val lr = List(
//     Runway("Runway South", List(Class1, Class2, Class3, Class4, Class5, Class6), List()),
//     Runway("Runway North", List(Class1, Class2, Class3, Class4, Class5, Class6), List()),
//     Runway("Runway Northwest", List(Class1, Class2), List()),
//     Runway("Runway West", List(Class4, Class5, Class6), List()),
// ) 

// bruteForce(Agenda(la, lr)) 

// // AgendaIO("files\\assessment\\ms01\\valid08_in.xml")
// // AgendaIO("files\\assessment\\ms01\\valid08_in.xml")
// // AgendaIO("files\\assessment\\ms01\\valid08_in.xml")

// // val schedule = Schedule(lr)

// def bruteForce_2(la: Seq[Aircraft], lr: Seq[Runway]): Option[Seq[Runway]] = la match
//     case Nil => Some(lr)
//     case a +: remainingAircrafts => 
//         val ls = lr.flatMap (
//             runway => assign(a, runway).fold(  
                
//                 error => error match
//                     case OperationTimeWindow(error) => 
//                         val allPoss = createAllPossibilities(runway, a)
//                         allPoss.minByOption(_.cost) match
//                             case Some(r) => bruteForce_2(remainingAircrafts, Runway.update(lr, r))
//                             case None => Nil
//                     case _ => Nil
                
//                 , updatedRunway => 

//                 if runway.cost < updatedRunway.cost then
//                     val allPoss = createAllPossibilities(runway, a)
//                     allPoss.minByOption(_.cost) match
//                         case Some(r) => bruteForce_2(remainingAircrafts, Runway.update(lr, r))
//                         case None => bruteForce_2(remainingAircrafts, Runway.update(lr, updatedRunway))
//                 else
//                     bruteForce_2(remainingAircrafts, Runway.update(lr, updatedRunway))
//             )
//         )
//         ls.minByOption(Runway.cost(_))

// def bruteForce(a: Agenda): Result[Seq[Runway]] = {
//   val aircrafts = createGroups(a.aircrafts)
  
//   def bruteForceHelper(aircrafts: Seq[Seq[Aircraft]], lr: Seq[Runway]): Result[Seq[Runway]] = aircrafts match {
//     case Nil => Right(lr)
//     case aircraftList +: remainingAircrafts =>
//       bruteForce_2(aircraftList, a.runways) match {
//         case Some(newSchedule) => 
//             bruteForceHelper(remainingAircrafts, newSchedule)
//         case None => Left(DomainError.Error("Deu nao o"))
//       }
//   }
  
//   bruteForceHelper(aircrafts, a.runways)
// }

// // for
// //     a <- AgendaIO("files\\assessment\\ms01\\valid_FRA_01_in.xml")
// //     s <- bruteForce(a) 
// //     // match
// //     //     case Right(s) => 
                
// //     //         Right(
// //     //             <schedule cost={s.cost.toString} xsi:schemaLocation="http://www.dei.isep.ipp.pt/tap-2023 ../../schedule.xsd " xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.dei.isep.ipp.pt/tap-2023">
// //     //                 {s.runways.map(r => (r.aircrafts, r)).flatMap(t => 
// //     //                     {
// //     //                         t._1.map(a => 
// //     //                             <aircraft id={a.id} runway={t._2.id} time={a.time.getOrElse(-1).toString}/>
// //     //                         )
// //     //                     }
// //     //                 )}
// //     //             </schedule>
// //     //         )
// //     //     case _ => -999
// //     c <- Right(Runway.cost(s))
// // yield s





// // def GROUP_SIZE: Int = 10

// // type Partition = (Seq[Aircraft], Seq[Aircraft])



// // def createGroups(l: List[Aircraft]): List[List[Aircraft]] = l match
// //     case Nil => Nil
// //     case _ => {
// //         val (chunk, remaining) = l.splitAt(GROUP_SIZE)
// //         chunk :: createGroups(remaining)
// //     }

// // def createPartitions(aircrafts: Seq[Aircraft]): Seq[Partition] = 
// //     @tailrec
// //     def loop(la: Seq[Aircraft], lp: Seq[Partition]): Seq[Partition] = la match 
// //         case Nil => lp
// //         case head +: remaining =>
// //             val scheduled = lp.lastOption.fold(Seq(head))(_._1 :+ head)
// //             loop(remaining, lp :+ (scheduled, remaining))

// //     loop(aircrafts, Seq((Seq.empty, aircrafts)))

// // def createAllPossibilities(r: Runway, a: Aircraft): Seq[Runway] = 
// //     createPartitions(r.aircrafts).flatMap { case (la, remaining) => 
// //         for
// //             r <- assign(a, r.setAircrafts(la)).toOption
// //             r <- assign(remaining, r).toOption
// //         yield r
// //     }