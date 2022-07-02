package com.meetup.swcraftersmurcia

import cats.syntax.all._
import com.meetup.swcraftersmurcia.DniValidator._
import munit.{FunSuite, ScalaCheckSuite}
import org.scalacheck.Prop.{forAll, forAllNoShrink}

class DniSpec extends FunSuite with ScalaCheckSuite {

  Fixtures.validDniInputs
    .foreach { validDni =>
      test(s"Sample ${validDni} should be validated as correct DNI") {
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

  test("Raw input must not end with a digit") {
    forAllNoShrink(Generators.notLastCharWithNumGen) { notLastCharLetter =>
      val expected = DniError.NotLastLetter

      val result = validateDNI(notLastCharLetter)

      assertEquals(result, expected.invalidNec[Dni])
    }
  }

  test("Raw input must not end with a forbidden letter") {
    forAllNoShrink(Generators.notLastCharWithForbiddenGen) {
      notLastCharValidLetter =>
        val expected = DniError.NotLastValidLetter

        val result = validateDNI(notLastCharValidLetter)

        assertEquals(result, expected.invalidNec[Dni])
    }
  }

  test("Raw input prefix (exactly 8 characters) must be digits") {
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

  test(
    "Raw input with exactly one invalid letter at the beginning must be rejected"
  ) {
    forAllNoShrink(Generators.invalidNieStartGen) { invalidNieFirst =>
      val expected = DniError.NotFirstNieLetter

      val result = validateDNI(invalidNieFirst)

      assertEquals(result, expected.invalidNec)
    }
  }

}
