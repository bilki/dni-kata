package com.meetup.swcraftersmurcia

import cats.data.ValidatedNec
import cats.syntax.all._

object DniValidator {

  case class Dni(value: String)

  case class Error()

  def validateDNI(rawInput: String): ValidatedNec[Error, Dni] =
    Dni(rawInput).validNec[Error]

}
