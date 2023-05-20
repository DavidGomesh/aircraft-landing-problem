package pj.domain.simpleTypesTest.GenInstance

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import pj.domain.simpleTypes.Identifier
import pj.domain.simpleTypes.ClassType
import pj.domain.simpleTypes.NonNegativeInteger
import pj.domain.simpleTypes.PositiveInteger
import pj.domain.model.Aircraft
import pj.domain.model.Runway

object GenAircraftTypes extends Properties("GenAircraftTypes"):

    def genIdentifier: Gen[Identifier] = Gen.listOfN(4, Gen.alphaNumChar).map(_.mkString).flatMap(str => 
      Identifier.apply(str).fold(_=> Gen.fail, Gen.const))

    def genClassType: Gen[ClassType] = Gen.choose[Byte](1, 6).flatMap(n => 
      ClassType.apply(n).fold(_=> Gen.fail, Gen.const))

    def genNonNegativeInteger: Gen[NonNegativeInteger] = Gen.choose(0, 100).flatMap(number =>
      NonNegativeInteger.apply(number).fold(_ => Gen.fail, Gen.const))

    def genPositiveIntegerOption: Gen[Option[PositiveInteger]] = Gen.option(Gen.posNum[Int].flatMap { intValue =>
      PositiveInteger.apply(intValue) match {
        case Right(positiveInteger) => Gen.const(positiveInteger)
        case Left(_) => Gen.fail[PositiveInteger]
      }
    })

    def genPositiveInteger: Gen[PositiveInteger] = Gen.choose(1, 10).flatMap(PositiveInteger.apply(_).fold(_ => Gen.fail, Gen.const))

    def genAircraftTime: Gen[NonNegativeInteger] = Gen.choose(0, 100).flatMap(number =>
      NonNegativeInteger.apply(number).fold(_ => Gen.fail, Gen.const))

