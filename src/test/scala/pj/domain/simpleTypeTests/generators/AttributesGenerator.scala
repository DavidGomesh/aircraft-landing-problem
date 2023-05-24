package domain
import org.scalacheck.Properties
import org.scalacheck.Gen
import simpleTypes.identifier.*
import enumerate.ClassType
import simpleTypes.integer.*


object AttributesGenerator extends Properties("AttributesGenerator"):

    def genAircraftId(numCharacters: Int): Gen[AircraftId] = 
      Gen.listOfN(numCharacters, Gen.alphaNumChar).map(_.mkString).flatMap(str => 
        AircraftId.apply(str).fold(_=> Gen.fail, Gen.const)
      )

    def genRunwayId(numCharacters: Int): Gen[RunwayId] = 
      Gen.listOfN(numCharacters, Gen.alphaNumChar).map(_.mkString).flatMap(str => 
        RunwayId.apply(str).fold(_=> Gen.fail, Gen.const)
    )

    def genClassType: Gen[ClassType] = Gen.oneOf(ClassType.values)
        
    def genAircraftTarget(minValue: Int, maxValue: Int): Gen[NonNegativeInt] = 
      Gen.choose(minValue, maxValue).flatMap(number =>
        NonNegativeInt.apply(number).fold(_ => Gen.fail, Gen.const)
      )

    def genAircraftTime: Gen[Option[NonNegativeInt]] = 
      Gen.option(Gen.posNum[Int].flatMap { value =>
        NonNegativeInt.apply(value) match {
          case Right(positiveInt) => Gen.const(positiveInt)
          case Left(_) => Gen.fail[NonNegativeInt]
        }
      })

    def genAircraftEmergency: Gen[Option[PositiveInt]] = 
      Gen.option(Gen.posNum[Int].flatMap { value =>
        PositiveInt.apply(value) match {
          case Right(positiveInt) => Gen.const(positiveInt)
          case Left(_) => Gen.fail[PositiveInt]
        }
      })

    def genClassRunway: Gen[List[ClassType]] = 
      for
          genValue <- Gen.choose(1, 6)
          classRunway <- Gen.listOfN(genValue, genClassType)
      yield classRunway
