package com.meetup.swcraftersmurcia

import cats.data.{Validated, ValidatedNec}
import cats.syntax.all._
import com.meetup.swcraftersmurcia.DniError.{NotLastLetter, NotNineLong}

object DniValidator {

  case class Dni(value: String)

  private def validateNineCharsLong(input: String): ValidatedNec[NotNineLong, String] =
    Validated.cond(input.length == Constants.DNI_LENGTH, input, NotNineLong).toValidatedNec

  private def validateLastCharNotNum(input: String): ValidatedNec[NotLastLetter, String] =
    Validated.cond(!input.last.isDigit, input, NotLastLetter).toValidatedNec

  def validateDNI(rawInput: String): ValidatedNec[DniError, Dni] =
    validateNineCharsLong(rawInput)
      .andThen(validateLastCharNotNum)
      .map(Dni)

}
