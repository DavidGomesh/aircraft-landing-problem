package pj.domain.Properties

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import pj.domain.simpleTypes.Identifier
import pj.domain.simpleTypes.ClassType
import pj.domain.simpleTypes.NonNegativeInteger
import pj.domain.simpleTypes.PositiveInteger
import pj.domain.model.Aircraft
import pj.domain.simpleTypesTest.GenInstance.GenAircraftTypes.genIdentifier
import pj.domain.simpleTypesTest.GenInstance.GenAircraftTypes.genClassType
import pj.domain.simpleTypesTest.GenInstance.GenAircraftTypes.genPositiveInteger
import pj.domain.simpleTypesTest.GenInstance.GenAircraftTypes.genNonNegativeInteger
import pj.domain.model.Runway
import pj.domain.Properties.RunwaysProperties.genRunway
import pj.domain.simpleTypesTest.GenInstance.GenAgenda.genRunways

object AircraftProperties extends Properties("AircraftProperties"):

    def genAircraftId: Gen[Identifier] = genIdentifier
    def genAircraftClass: Gen[ClassType] = genClassType
    def genAircraftTarget: Gen[NonNegativeInteger] = genNonNegativeInteger
    def genAircraftEmergency: Gen[Option[PositiveInteger]] = Gen.option(genPositiveInteger)
    def genAircraftTimeNew: Gen[NonNegativeInteger] = genNonNegativeInteger
    
    def genAircraft(runways:  List[Runway]): Gen[Aircraft] =
      for 
        id <- genAircraftId
        classType <-  Gen.oneOf(runways.flatMap(_.classes))
        target <- genAircraftTarget
        emergency <- Gen.frequency(4 -> None, 1 -> genAircraftEmergency)
      yield Aircraft(id, classType, target, None, NonNegativeInteger.zero)

    property("Generated Aircraft") = forAll(genRunways) { runways =>
      forAll(genAircraft(runways)) { aircraft =>
          println("Aircraft"+aircraft)
          val aircraftClass = aircraft.classType
          val runwayClasses = runways.flatMap(_.classes)
          runwayClasses.contains(aircraftClass)

      }
    }
