package pj.domain.Properties

import org.scalacheck.Properties
import pj.domain.model.Aircraft
import pj.domain.model.Runway
import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import pj.domain.schedule.ScheduleMS01.schedule
import pj.domain.Properties.RunwaysProperties.genRunway
import pj.domain.Properties.AircraftProperties.genAircraft
import pj.domain.simpleTypesTest.GenInstance.GenAgenda.genRunways
import pj.domain.simpleTypesTest.GenInstance.GenAircraftTypes.genIdentifier
import pj.domain.Properties.AgendaProperties.genAgenda


object ScheduleProperties extends Properties("ScheduleProperties"):

    
    property("Each aircraft is programmed on a compatible runway") = forAll(genAgenda) { 
        (runways, aircrafts) => {
            val scheduled = schedule(aircrafts, runways, 0)
            scheduled match
                case Right((_, runway)) => 
                    aircrafts.forall(a => runway.flatMap(_.aircrafts.map(_.id)).contains(a.id))
                case _ => false
        }
    } 
