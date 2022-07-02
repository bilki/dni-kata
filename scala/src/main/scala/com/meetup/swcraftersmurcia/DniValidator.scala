package com.meetup.swcraftersmurcia

import cats.data.ValidatedNec
import cats.syntax.all._

object DniValidator {

  case class Dni(value: String)

  def validateDNI(rawInput: String): ValidatedNec[DniError, Dni] =
    Dni(rawInput).validNec[DniError]

}
