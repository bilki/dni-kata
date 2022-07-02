package com.meetup.swcraftersmurcia

import cats.data.ValidatedNec

object DniValidator {

  case class Dni(value: String)

  case class Error()

  def validateDNI(rawInput: String): ValidatedNec[Error, Dni] = ???

}
