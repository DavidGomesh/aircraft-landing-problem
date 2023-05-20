package pj.domain.Properties

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import pj.domain.simpleTypes.PositiveInteger
import pj.domain.simpleTypes.*
import pj.domain.model.Agenda
import pj.domain.model.Runway
import pj.domain.model.Aircraft
import pj.domain.simpleTypesTest.GenInstance.GenAgenda.genRunways
import pj.domain.Properties.AircraftProperties.genAircraft

object AgendaProperties extends Properties("AgendaProperties"):

    def genAgenda =
        for
            runway <- genRunways
            num <- Gen.choose(20, 1000)
            aircrafts <- Gen.listOfN(10, genAircraft(runway))
        yield (runway, aircrafts.sortBy(_.target.toInt))

        
    // property("Generated Agenda") = forAll(genAgenda) { sc =>
    //         sc.aircrafts.nonEmpty
    //     }

    // property("Generated Agenda with runways no Empty") = forAll(genAgenda) { sc =>
    //         sc.runways.nonEmpty
    //     }
