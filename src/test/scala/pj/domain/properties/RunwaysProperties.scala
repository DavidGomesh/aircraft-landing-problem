package pj.domain.properties

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import pj.domain.model.Runway
import pj.domain.simpleTypesTest.generators.AttributeGenerator.*


object RunwaysProperties extends Properties("RunwaysProperties"):

    def genRunways: Gen[List[Runway]] = 
        for
            num <- Gen.choose(50, 500)
            runways <- Gen.listOfN(num, genRunway)
        yield runways

    def genRunway: Gen[Runway] =
        for{
            id <- genIdentifier(4)
            classes <- genClassRunway
        } yield Runway(id, classes.distinct, List())

    property("Generated Runway") = forAll(genRunway) { runway =>
        println("Runways"+runway)
        runway.classes.nonEmpty
    }
