package com.meetup.swcraftersmurcia

import com.meetup.swcraftersmurcia.DocumentValidator.NotValidDni
import munit.FunSuite

class KataSpec extends FunSuite {

  test("DNI or NIE size should be of size 9") {
    val input = "123456789X"

    val expected = Left(NotValidDni("size not equal to 9"))

    assertEquals(DocumentValidator.validate(input), expected)
  }

}
