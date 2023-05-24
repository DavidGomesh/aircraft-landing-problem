package domain.properties

import org.scalacheck.Properties
import org.scalacheck.Gen
import domain.Runway
import domain.Aircraft
import domain.AttributesGenerator.genAircraftId
import simpleTypes.identifier.*
import domain.AttributesGenerator.*
import simpleTypes.integer.NonNegativeInt


object AircraftsProperties extends Properties("AircraftsProperties"): 

    def genAircrafts(runways: List[Runway]): Gen[List[Aircraft]] =
      for 
        num <- Gen.choose(1, 10)
        lid <- Gen.listOfN(num, genAircraftId(4))
        aircrafts <- Gen.sequence[List[Aircraft], Aircraft](lid.distinct.map(genAircraft(_, runways)))
      yield aircrafts

    def genAircraft(id: AircraftId, runways: List[Runway]): Gen[Aircraft] =
      for 
        classType <-  Gen.oneOf(runways.flatMap(_.classes))
        target <- genAircraftTarget(0, 2000)
        emergency <- Gen.frequency(5 -> None, 1 -> genAircraftEmergency)
      yield Aircraft(id, classType, target, emergency, None)