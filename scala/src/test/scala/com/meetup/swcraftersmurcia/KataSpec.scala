package com.meetup.swcraftersmurcia

import com.meetup.swcraftersmurcia.DocumentValidator.{Dni, NotValidDni}
import munit.FunSuite

class KataSpec extends FunSuite {

  test("DNI or NIE size should return an error if input size is not 9") {
    val input = "123456789X"

    val expected = Left(NotValidDni("size not equal to 9"))

    val result = DocumentValidator.validate(input)

    assertEquals(result, expected)
  }

  test("DNI or NIE should be of size 9") {
    val input = "12345678X"

    val expected = Right(Dni(input))

    val result = DocumentValidator.validate(input)

    assertEquals(result, expected)
  }

}
