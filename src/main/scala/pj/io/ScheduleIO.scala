package pj.io

import pj.domain.Plan
import scala.xml.Elem
import pj.domain.Result

object ScheduleIO:

    // def save(p: Plan, path: String): Unit =
    //     FileIO.save(path, planToXml(p))

    def planToXml(p: Plan): Result[Elem] = 
        Right(
            <schedule cost={p.cost.toString} xsi:schemaLocation="http://www.dei.isep.ipp.pt/tap-2023 ../../schedule.xsd " xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.dei.isep.ipp.pt/tap-2023">
                {p.runways.map(r => (r.aircrafts, r)).flatMap((la, r) => 
                    la.map(a => <aircraft id={a.id} runway={r.id} time={a.time.getOrElse(-1).toString.trim}/>)
                )}
            </schedule>
        )