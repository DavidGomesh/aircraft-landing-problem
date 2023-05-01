package pj.domain.simpleTypesTest

package pj.domain.simpleTypesTest

import org.scalatest.funsuite.AnyFunSuite
import _root_.pj.domain.simpleTypes.ClassType
import _root_.pj.domain.DomainError.*
import _root_.pj.domain.simpleTypes.Identifier
import _root_.pj.domain.simpleTypes.NonNegativeInteger
import _root_.pj.domain.simpleTypes.PositiveInteger


class SimpleTypesTest extends AnyFunSuite:
    test("ClassType returns Right for valid classType values") {
    val classTypes = Seq[Byte](1, 2, 3, 4, 5, 6)
    classTypes.foreach { classType =>
      val result = ClassType(classType)
      assert(result.equals(Right(classType)))
    }
  }

    test("ClassType returns Left for invalid classType value") {
    val invalidClassType: Byte = 0
    val result = ClassType(invalidClassType)
    assert(result == Left(IllegalArgumentError("Invalid class type")))
  }

    test("Identifier returns Right for valid input") {
    val validInput = "valid-identifier"
    val result = Identifier(validInput)
    assert(result.equals(Right(validInput)))
    }

    test("NonNegativeInteger returns Right for valid non-negative integers") {
    val numbers = Seq(0, 1, 10, 100)
    numbers.foreach { n =>
        val result = NonNegativeInteger(n)
        assert(result.equals(Right(n)))
    }
    }

    test("NonNegativeInteger returns Left for invalid non-negative integers") {
    val numbers = Seq(-1, -10, -210)
    numbers.foreach { n =>
        val result = NonNegativeInteger(n)
        assert(result == Left(IllegalArgumentError("Invalid non negative integer")))
    }
    }


    test("PositiveInteger returns Right for valid positive integer values") {
    val positiveIntegers = Seq(1, 2, 3, 4, 5, 100)
    positiveIntegers.foreach { n =>
        val result = PositiveInteger(n)
        assert(result.equals(Right(n)))
    }
    }

    test("PositiveInteger returns Left for invalid positive integer values") {
    val invalidIntegers = Seq(0, -1, -100)
    invalidIntegers.foreach { n =>
        val result = PositiveInteger(n)
        assert(result == Left(IllegalArgumentError("Invalid positive integer")))
    }
    }
