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
import pj.domain.simpleTypesTest.generators.AttributeGenerator.*


object AircraftProperties extends Properties("AircraftProperties"):   
   
    def genAircraft(runways:  List[Runway]): Gen[Aircraft] =
      for 
        id <- genIdentifier(4)
        classType <-  Gen.oneOf(runways.flatMap(_.classes))
        target <- genAircraftTarget(0, 2000)
        emergency <- Gen.frequency(5 -> None, 1 -> genAircraftEmergency)
      yield Aircraft(id, classType, target, None, NonNegativeInteger.zero)

    property("Generated Aircraft") = forAll(genRunways) { runways =>
      forAll(genAircraft(runways)) { aircraft =>
          println("Aircraft"+aircraft)
          val aircraftClass = aircraft.classType
          val runwayClasses = runways.flatMap(_.classes)
          runwayClasses.contains(aircraftClass)

      }
    }
