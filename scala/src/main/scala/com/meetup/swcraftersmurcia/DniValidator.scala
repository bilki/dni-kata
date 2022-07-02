package com.meetup.swcraftersmurcia

import cats.data.{Validated, ValidatedNec}
import cats.syntax.all._
import com.meetup.swcraftersmurcia.DniError.NotNineLong

object DniValidator {

  case class Dni(value: String)

  private def validateNineCharsLong(input: String): ValidatedNec[DniError, String] =
    Validated.cond(input.length == 9, input, NotNineLong).toValidatedNec

  def validateDNI(rawInput: String): ValidatedNec[DniError, Dni] =
    validateNineCharsLong(rawInput).map(Dni)

}
