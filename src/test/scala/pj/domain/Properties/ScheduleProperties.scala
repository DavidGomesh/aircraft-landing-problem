package pj.domain.Properties

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import pj.domain.model.Aircraft
import pj.domain.simpleTypes.PositiveInteger
import pj.domain.model.Runway
import pj.domain.schedule.ScheduleMS01.schedule
import pj.domain.Properties.RunwaysProperties.genRunway
import pj.domain.Properties.AircraftProperties.genAircraft
import pj.domain.simpleTypesTest.GenInstance.GenAgenda.genRunways
import pj.domain.simpleTypesTest.GenInstance.GenAircraftTypes.genIdentifier
import pj.domain.Properties.AgendaProperties.genAgenda
import pj.domain.simpleTypes.*
import org.scalatest.run

object ScheduleProperties extends Properties("ScheduleProperties"):

    property("In a valid schedule, every aircraft was assigned a runway;") = forAll(genAgenda) {
        (runways, aircrafts) => {
            val scheduled = schedule(aircrafts, runways, 0)
            scheduled.fold(_ => false, (_, runway) => {
                aircrafts.forall(a => runway.flatMap(_.aircrafts.map(_.id)).contains(a.id))
            })
        }
    }

     property("Each aircraft should be scheduled for a runway which can handle it") = forAll(genAgenda) { 
        (runways, aircrafts) =>{
            val scheduled = schedule(aircrafts, runways, 0)
            scheduled.fold(_ => false, (_, runway) => {
                aircrafts.forall(a => runway.flatMap(_.aircrafts.map(aircraft => 
                    (aircraft.id, aircraft.classType))).contains(a.id, a.classType))
            })
        }
    }

    property("An aircraft can never be scheduled before its “target time”") = forAll(genAgenda) { 
        (runways, aircrafts) =>{
            val scheduled = schedule(aircrafts, runways, 0)
            scheduled.fold(_ => false, (_, runway) => {
                runway.flatMap(_.aircrafts).foldLeft(true)((acc, aircraft) => acc && aircraft.target.lessOrEquals(aircraft.time))
            })
        }
    }

    property("An emergency aircraft should never exceed its allotted time") = forAll(genAgenda) { 
        (runways, aircrafts) =>{
            val scheduled = schedule(aircrafts, runways, 0)
            scheduled.fold(_ => false, (_, runway) => {
                val emergencyAircrafts = runway.flatMap(_.aircrafts)
                    .filter(aircraft => aircraft.emergency.exists(greaterThan(PositiveInteger.zero)))
                    emergencyAircrafts.forall(aircraft => 
                        val timeEmergency = aircraft.emergency.fold(0)(value => value.to)
                        aircraft.time.toInt <= (aircraft.target.toInt + timeEmergency))
            
            })
        }
    }

    property("Schedule the non-emergency aircraft in a 'first come, first served' manner") = forAll(genAgenda) { 
        (runways, aircrafts) =>{
            val scheduled = schedule(aircrafts, runways, 0)
            scheduled.fold(_ => false, (_, runway) => {
                val byTarget = runways.flatMap(_.aircrafts.filter(_.emergency.isEmpty).sortBy(_.target.toInt))
                val byTime = runways.flatMap(_.aircrafts.filter(_.emergency.isEmpty).sortBy(_.time.toInt))
                byTarget == byTime
            })                            
        }
    }


    

    // a aeronave de emergencia deve ser adicionada na runways sem execeder seu tempo de emergencia
    
    
    // verificar se todas as aeronaves que não são de emergência, estão sendo inseridas nas runways na sequencia
    //primeiro a chegar, primeiro a ser servido -> como verificar ==> pegar a lista de aircrafts nas runways,
    // tirar as de emergencia e ordenar, depois verificar as duas listas (uma odernado por target e outra por time)
    // estão nas mesma posição da lista, ou seja, ordenadas igualmentes

