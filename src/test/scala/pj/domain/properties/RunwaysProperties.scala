package domain.properties

import org.scalacheck.Gen
import org.scalacheck.Properties
import domain.Runway
import domain.AttributesGenerator.genRunwayId
import domain.AttributesGenerator.genClassRunway

object RunwaysProperties extends Properties("RunwaysProperties"):

    def genRunways: Gen[List[Runway]] = 
        for
            num <- Gen.choose(1, 10)
            runways <- Gen.listOfN(num, genRunway)
        yield runways

    def genRunway: Gen[Runway] =
        for{
            id <- genRunwayId(4)
            classes <- genClassRunway
        } yield Runway(id, classes.distinct, List())
