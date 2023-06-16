package domain.properties

import org.scalacheck.Gen
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import domain.Runway
import domain.AttributesGenerator.genRunwayId
import domain.AttributesGenerator.genClassRunway

object RunwaysProperties extends Properties("RunwaysProperties"):

    val min = 1
    val max = 10
    val numCharId = 4

    def genRunways: Gen[Seq[Runway]] = 
        for
            num <- Gen.choose(min, max)
            runways <- Gen.listOfN(num, genRunway)
        yield runways

    def genRunway: Gen[Runway] =
        for{
            id <- genRunwayId(numCharId)
            classes <- genClassRunway
        } yield Runway(id, classes.distinct, List())

    property("Generated Runway") = forAll(genRunway) { runway =>
        runway.classes.nonEmpty
    }
