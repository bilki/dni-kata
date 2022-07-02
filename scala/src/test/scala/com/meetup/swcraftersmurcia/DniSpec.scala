package com.meetup.swcraftersmurcia

import cats.syntax.all._
import com.meetup.swcraftersmurcia.DniValidator._
import munit.{FunSuite, ScalaCheckSuite}
import org.scalacheck.Prop.forAll

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

}
