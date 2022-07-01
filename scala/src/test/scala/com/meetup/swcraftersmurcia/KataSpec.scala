package com.meetup.swcraftersmurcia

import com.meetup.swcraftersmurcia.DocumentValidator.{
  Dni,
  Messages,
  NotValidDni
}
import munit.FunSuite

class KataSpec extends FunSuite {

  test("DNI or NIE size should return an error if input size is not 9") {
    val input = "123456789X"

    val expected = Left(NotValidDni(Messages.sizeNotValidMsg))

    val result = DocumentValidator.validate(input)

    assertEquals(result, expected)
  }

  test("DNI or NIE should be of size 9") {
    val input = "54956042A"

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

  List(
    'U', 'I', 'O', 'Ñ', '9'
  ).foreach { lastLetter =>
    test(
      "DNI should return an error when the last character is not a valid letter"
    ) {
      val input = s"12345678${lastLetter}"

      val expected = Left(NotValidDni(Messages.prefixEndsWithLetter))

      val result = DocumentValidator.validate(input)

      assertEquals(result, expected)
    }
  }

  test(
    "DNI should return an error when the control digit is not valid"
  ) {
    val input = "12345678T"

    val expected = Left(NotValidDni(Messages.controlSumDigitNotValid))

    val result = DocumentValidator.validate(input)

    assertEquals(result, expected)
  }

//  List(
//    "31970165G",
//    "10448738E",
//    "68163822X",
//    "68132163E",
//    "50791233B",
//    "90250990W",
//    "87477013D",
//    "34272318H",
//    "54956042A",
//    "78176129A",
//    "49390008S",
//    "90583399S",
//    "08004624A",
//    "00062477D",
//    "94985972C",
//    "87819112Y",
//    "92683017D",
//    "17402629R",
//    "17206298K",
//    "24473205D"
//  ).foreach { validDni =>
//    test("DNI should be valid when control char is part of the valid values set") {
//      val expected = Right(Dni(validDni))
//
//      val result = DocumentValidator.validate(validDni)
//
//      assertEquals(result, expected)
//    }
//  }

}
