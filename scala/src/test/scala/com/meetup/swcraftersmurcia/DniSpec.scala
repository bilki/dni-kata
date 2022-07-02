package com.meetup.swcraftersmurcia

import cats.syntax.all._
import com.meetup.swcraftersmurcia.DniValidator._
import munit.{FunSuite, ScalaCheckSuite}
import org.scalacheck.Prop.{forAll, forAllNoShrink}

class DniSpec extends FunSuite with ScalaCheckSuite {

  test("A valid raw input should be validated as a correct DNI") {
    val input = Fixtures.validDniInputs.head

    val expected = Dni(input)

    val result = validateDNI(input)

    assertEquals(result, expected.validNec[DniError])
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
    forAllNoShrink(Generators.notLastCharWithForbiddenGen) { notLastCharValidLetter =>
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

}
