package com.meetup.swcraftersmurcia

import cats.syntax.all._
import com.meetup.swcraftersmurcia.DniValidator._
import munit.FunSuite

class DniSpec extends FunSuite {

  test("A valid raw input should be validated as a correct DNI") {
    val input = Fixtures.validDniInputs.head

    val expected = Dni(input)

    val result = validateDNI(input)

    assertEquals(result, expected.validNec[Error])
  }

}
