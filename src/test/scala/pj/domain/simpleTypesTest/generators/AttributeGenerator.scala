package pj.domain.simpleTypesTest.generators

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import pj.domain.simpleTypes.Identifier
import pj.domain.simpleTypes.ClassType
import pj.domain.simpleTypes.NonNegativeInteger
import pj.domain.simpleTypes.PositiveInteger
import pj.domain.model.Aircraft
import pj.domain.model.Runway

object AttributeGenerator extends Properties("GenAircraftTypes"):

    def genIdentifier(numCharacters: Int): Gen[Identifier] = 
      Gen.listOfN(numCharacters, Gen.alphaNumChar).map(_.mkString).flatMap(str => 
        Identifier.apply(str).fold(_=> Gen.fail, Gen.const)
      )

    def genClassType(minValue: Byte, maxValue: Byte): Gen[ClassType] = 
      Gen.choose[Byte](minValue, maxValue).flatMap(n => 
        ClassType.apply(n).fold(_=> Gen.fail, Gen.const)
      )

    def genAircraftTarget(minValue: Int, maxValue: Int): Gen[NonNegativeInteger] = 
      Gen.choose(minValue, maxValue).flatMap(number =>
        NonNegativeInteger.apply(number).fold(_ => Gen.fail, Gen.const)
      )

    def genAircraftTime(minValue: Int, maxValue: Int): Gen[NonNegativeInteger] = 
      Gen.choose(minValue, maxValue).flatMap(number =>
        NonNegativeInteger.apply(number).fold(_ => Gen.fail, Gen.const)
      )

    def genAircraftEmergency: Gen[Option[PositiveInteger]] = 
      Gen.option(Gen.posNum[Int].flatMap { value =>
        PositiveInteger.apply(value) match {
          case Right(positiveInteger) => Gen.const(positiveInteger)
          case Left(_) => Gen.fail[PositiveInteger]
        }
      })

    def genClassRunway: Gen[List[ClassType]] = 
      for
          genValue <- Gen.choose(1, 6)
          classRunway <- Gen.listOfN(genValue, genClassType(1,6))
      yield classRunway