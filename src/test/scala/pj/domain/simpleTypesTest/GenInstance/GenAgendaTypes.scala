package pj.domain.simpleTypesTest.GenInstance

import org.scalacheck.Properties
import org.scalacheck.Gen
import pj.domain.simpleTypes.PositiveInteger
import pj.domain.model.Runway
import pj.domain.model.Agenda
import pj.domain.Properties.RunwaysProperties.genRunway

object GenAgenda extends Properties("GenAgenda"):

    // def genMaximumDelayTime: Gen[PositiveInteger] = Gen.choose(0, Int.MaxValue).flatMap(num =>
    //   PositiveInteger.apply(num).fold(_ => Gen.fail, Gen.const))

    def genRunways: Gen[List[Runway]] = 
        for
            runways <- Gen.listOfN(2, genRunway)
        yield runways


