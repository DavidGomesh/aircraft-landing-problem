package pj.domain.schedule

import scala.xml.Elem
import pj.domain.Result
import pj.io.AgendaIO.xmlToAgenda
import pj.io.ScheduleIO.planToXml
import pj.domain.BruteForce.generatePlan

object ScheduleMS03 extends Schedule:

    // TODO: Create the code to implement a functional domain model for schedule creation
    //       Use the xml.XML code to handle the xml elements
    //       Refer to https://github.com/scala/scala-xml/wiki/XML-Processing for xml creation
    def create(xml: Elem): Result[Elem] =
        for
            ag <- xmlToAgenda(xml)
            gp <- generatePlan(ag)
            pl <- planToXml(gp)
        yield pl