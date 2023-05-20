package pj.domain.simpleTypesTest.GenInstance

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import pj.domain.simpleTypes.ClassType
import pj.domain.model.Runway
import pj.domain.simpleTypesTest.GenInstance.GenAircraftTypes.genIdentifier
import pj.domain.simpleTypesTest.GenInstance.GenAircraftTypes.genClassType

object GenRunwayTypes extends Properties("GenRunways"):

    def genClass: Gen[List[ClassType]] = 
        for
            n <- Gen.choose(1, 6)
            c <- Gen.listOfN(n, genClassType)
        yield c



