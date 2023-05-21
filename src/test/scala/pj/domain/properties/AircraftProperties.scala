package pj.domain.properties

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import pj.domain.simpleTypes.Identifier
import pj.domain.simpleTypes.ClassType
import pj.domain.simpleTypes.NonNegativeInteger
import pj.domain.simpleTypes.PositiveInteger
import pj.domain.model.Aircraft
import pj.domain.model.Runway
import pj.domain.properties.AgendaProperties.*
import pj.domain.properties.RunwaysProperties.genRunways
import pj.domain.simpleTypesTest.generators.AttributeGenerator.*


object AircraftProperties extends Properties("AircraftProperties"): 

    def genAircrafts(runways: List[Runway]): Gen[List[Aircraft]] =
      for 
        num <- Gen.choose(20, 500)
        lid <- Gen.listOfN(num, genIdentifier(1))
        aircrafts <- Gen.sequence[List[Aircraft], Aircraft](lid.distinct.map(genAircraft(_, runways)))
      yield aircrafts

    def genAircraft(id: Identifier, runways: List[Runway]): Gen[Aircraft] =
      for 
        classType <-  Gen.oneOf(runways.flatMap(_.classes))
        target <- genAircraftTarget(0, 2000)
        emergency <- Gen.frequency(5 -> None, 1 -> genAircraftEmergency)
      yield Aircraft(id, classType, target, emergency, NonNegativeInteger.zero)

    property("Generated Aircraft") = forAll(genRunways, genIdentifier(4)) { (runways, id) =>
      forAll(genAircraft(id, runways)) { aircraft =>
          val aircraftClass = aircraft.classType
          val runwayClasses = runways.flatMap(_.classes)
          runwayClasses.contains(aircraftClass)
      }
    }
