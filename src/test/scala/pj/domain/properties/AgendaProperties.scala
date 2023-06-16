package pj.domain.properties

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import domain.properties.RunwaysProperties.genRunways
import domain.properties.AircraftsProperties.genAircrafts
import domain.Agenda


object AgendaProperties extends Properties("AgendaProperties"):

    def genAgenda: Gen[Agenda] =
        for
            runways <- genRunways
            aircrafts <- genAircrafts(runways)
        yield Agenda(aircrafts.sortBy(_.target), runways)
        

    property("Generated Agenda") = forAll(genAgenda) { (agenda) =>
        val aircrafts = agenda.aircrafts
        val runways = agenda.runways
        aircrafts.nonEmpty && runways.nonEmpty
    }
