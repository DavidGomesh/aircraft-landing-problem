package pj.domain.properties

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import pj.domain.simpleTypes.PositiveInteger
import pj.domain.simpleTypes.*
import pj.domain.model.Agenda
import pj.domain.model.Runway
import pj.domain.model.Aircraft
import pj.domain.properties.RunwaysProperties.genRunways
import pj.domain.properties.AircraftProperties.genAircrafts

object AgendaProperties extends Properties("AgendaProperties"):

    def genAgenda =
        for
            runways <- genRunways
            aircrafts <- genAircrafts(runways)
        yield (runways, aircrafts.sortBy(_.target.toInt))

    property("Generated Agenda") = forAll(genAgenda) { (aircrafts, runways) =>
        aircrafts.nonEmpty && runways.nonEmpty
    }
