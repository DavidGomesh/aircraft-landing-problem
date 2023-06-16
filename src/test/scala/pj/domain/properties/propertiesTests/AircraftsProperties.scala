package domain.properties

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import org.scalacheck.Gen
import domain.Runway
import domain.Aircraft
import simpleTypes.identifier.*
import domain.AttributesGenerator.*
import simpleTypes.integer.NonNegativeInt
import domain.properties.RunwaysProperties.genRunways


object AircraftsProperties extends Properties("AircraftsProperties"):

    val min = 1
    val max = 6
    val maxDelay = 900
    val numCharId = 4

    def genAircrafts(runways: Seq[Runway]): Gen[Seq[Aircraft]] =
      for 
        num <- Gen.choose(min, max)
        lid <- Gen.listOfN(num, genAircraftId(numCharId))
        aircrafts <- Gen.sequence[List[Aircraft], Aircraft](lid.distinct.map(genAircraft(_, runways)))
      yield aircrafts

    def genAircraft(id: AircraftId, runways: Seq[Runway]): Gen[Aircraft] =
      for 
        classType <-  Gen.oneOf(runways.flatMap(_.classes))
        target <- genAircraftTarget(min, maxDelay)
        maxDelayTime = target + maxDelay
      yield Aircraft(id, classType, target, maxDelayTime, Some(0))


    property("Generated Aircraft") = forAll(genRunways, genRunwayId(numCharId)) { (runways, id) =>
      forAll(genAircraft(id, runways)) { aircraft =>
          val aircraftClass = aircraft.classType
          val runwayClasses = runways.flatMap(_.classes)
          runwayClasses.contains(aircraftClass)
      }
    }