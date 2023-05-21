package pj.domain.properties

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import pj.domain.simpleTypes.PositiveInteger
import pj.domain.simpleTypes.*
import pj.domain.model.Agenda
import pj.domain.model.Runway
import pj.domain.model.Aircraft
import pj.domain.properties.RunwaysProperties.genRunway
import pj.domain.properties.AircraftProperties.genAircraft

object AgendaProperties extends Properties("AgendaProperties"):

    def genRunways: Gen[List[Runway]] = 
        for
            num <- Gen.choose(50, 500)
            runways <- Gen.listOfN(num, genRunway)
        yield runways

    def genAgenda =
        for
            runway <- genRunways
            num <- Gen.choose(20, 500)
            aircrafts <- Gen.listOfN(num, genAircraft(runway))
        yield (runway, aircrafts.sortBy(_.target.toInt))

        
    property("Generated Agenda") = forAll(genAgenda) { (aircrafts, runways) =>
            aircrafts.nonEmpty
            runways.nonEmpty
        }
