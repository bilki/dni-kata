package com.meetup.swcraftersmurcia

import com.meetup.swcraftersmurcia.DocumentValidator.{Dni, Messages, NotValidDni}
import munit.FunSuite

class KataSpec extends FunSuite {

  test("DNI or NIE size should return an error if input size is not 9") {
    val input = "123456789X"

    val expected = Left(NotValidDni(Messages.sizeNotValidMsg))

    val result = DocumentValidator.validate(input)

    assertEquals(result, expected)
  }

  test("DNI or NIE should be of size 9") {
    val input = "12345678X"

    val expected = Right(Dni(input))

    val result = DocumentValidator.validate(input)

    assertEquals(result, expected)
  }

  test("DNI should return an error if input does not start with 8 digits") {
    val input = "A2345678X"

    val expected = Left(NotValidDni(Messages.prefixNotValidMsg))

    val result = DocumentValidator.validate(input)

    assertEquals(result, expected)
  }

  test("DNI should end with a letter") {
    val input = "123456789"

    val expected = Left(NotValidDni(Messages.prefixEndsWithLetter))

    val result = DocumentValidator.validate(input)

    assertEquals(result, expected)
  }



}
