package com.meetup.swcraftersmurcia

object DocumentValidator {

  case class Dni(value: String)

  case class NotValidDni(msg: String)

  def validate(rawInput: String): Either[NotValidDni, Dni] =
    if (rawInput.length != 9) Left(NotValidDni("size not equal to 9"))
    else Right(Dni(rawInput))

}
