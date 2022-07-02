package com.meetup.swcraftersmurcia

import cats.syntax.all._
import com.meetup.swcraftersmurcia.DniValidator._
import munit.{FunSuite, ScalaCheckSuite}
import org.scalacheck.Prop.{forAll, forAllNoShrink}

class DniSpec extends FunSuite with ScalaCheckSuite {

  Fixtures.validDniInputs
    .foreach { validDni =>
      test(s"Sample DNI ${validDni} should be validated as correct input") {
        val expected = Dni(validDni)

        val result = validateDNI(validDni)

        assertEquals(result, expected.validNec)
      }
    }

  test("Raw input other than nine characters long should be rejected") {
    forAll(Generators.notNineCharsGen) { notNineLong =>
      val expected = DniError.NotNineLong

      val result = validateDNI(notNineLong)

      assertEquals(result, expected.invalidNec[Dni])
    }
  }

  test("Raw input must end with a letter") {
    forAllNoShrink(Generators.notLastCharWithNumGen) { notLastCharLetter =>
      val expected = DniError.NotLastLetter

      val result = validateDNI(notLastCharLetter)

      assertEquals(result, expected.invalidNec[Dni])
    }
  }

  test("Raw input must end with a valid letter") {
    forAllNoShrink(Generators.notLastCharWithForbiddenGen) {
      notLastCharValidLetter =>
        val expected = DniError.NotLastValidLetter

        val result = validateDNI(notLastCharValidLetter)

        assertEquals(result, expected.invalidNec[Dni])
    }
  }

  test("Raw input first 8 characters must be digits") {
    forAllNoShrink(Generators.notAllDigitsPrefixGen) { notAllDigitsPrefix =>
      val expected = DniError.NotValidPrefix

      val result = validateDNI(notAllDigitsPrefix)

      assertEquals(result, expected.invalidNec[Dni])
    }
  }

  test("Only a single digit control should be valid at the same time") {
    forAllNoShrink(Generators.allPossibleCombinationsGen) {
      allPossibleCombinations =>
        val validCombinations =
          allPossibleCombinations.count(validateDNI(_).isValid)

        assertEquals(validCombinations, 1)
    }
  }

}
