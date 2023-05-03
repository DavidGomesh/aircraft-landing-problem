
import scala.xml.*
import pj.domain.simpleTypes.*
import pj.domain.schedule.ScheduleMS01
import pj.domain.model.Agenda
import pj.io.AgendaIO

@main
def main =
    val filePath = "files/assessment/ms01/valid01_in.xml"
    
    final case class Response(id: String, runway: String, time: String)
    
    val resp = AgendaIO.xmlToAgenda(filePath) match
        case Right(agenda) => {
            val response = ScheduleMS01.schedule(agenda.aircrafts, agenda.runways, 0)
            response match
                case Right(runways) => {
                    val resp = runways._2.flatMap(runway => runway.aircrafts.map(aircraft => {
                        Response(aircraft.id.to, runway.id.to, aircraft.time.toString)
                    })).sortBy(_.time.toInt)
                    
                    <schedule xsi:schemaLocation="http://www.dei.isep.ipp.pt/tap-2023 ../../schedule.xsd " xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.dei.isep.ipp.pt/tap-2023">
                        {resp.map(item => <aircraft id={item.id} runway={item.runway} time={item.time}/>)}
                    </schedule>
                }
                case Left(error) => Left(error)
        }
        case Left(error) => Left(error)

