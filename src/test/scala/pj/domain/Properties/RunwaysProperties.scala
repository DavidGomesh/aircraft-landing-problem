package pj.domain.Properties

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import pj.domain.simpleTypesTest.GenInstance.GenAircraftTypes.genIdentifier
import pj.domain.simpleTypesTest.GenInstance.GenRunwayTypes.genClass
import pj.domain.model.Runway

object RunwaysProperties extends Properties("RunwaysProperties"):

    def genRunway: Gen[Runway] =
        for{
            id <- genIdentifier
            classes <- genClass
        } yield Runway(id, classes.distinct, List())

    property("Generated Runway") = forAll(genRunway) { runway =>
        println("Runways"+runway)
        runway.classes.nonEmpty
    }
