package com.meetup.swcraftersmurcia

object DocumentValidator {

  object Messages {

    val sizeNotValidMsg = "size not equal to 9"
    val prefixNotValidMsg = "dni prefix should be all numbers"
    val prefixEndsWithLetter = "dni should end with a letter"
    val controlSumDigitNotValid = "control digit not valid"

  }

  sealed abstract class IdentityDoc(value: String)

  case class Dni(value: String) extends IdentityDoc(value)

  case class Nie(value: String) extends IdentityDoc(value)

  case class NotValidDni(msg: String)

  def validate(rawInput: String): Either[NotValidDni, Dni] =
    if (rawInput.length != 9) Left(NotValidDni(Messages.sizeNotValidMsg))
    else {
      val prefix = rawInput.take(8)
      if (prefix.forall(_.isDigit)) {
        val lastLetter = rawInput.reverse.head
        if (Remainder.letter.values.toList.contains(lastLetter)) {
          val remainder = prefix.toLong % 23
          if (Remainder.letter.get(remainder).contains(lastLetter))
            Right(Dni(rawInput))
          else
            Left(NotValidDni(Messages.controlSumDigitNotValid))
        } else
          Left(NotValidDni(Messages.prefixEndsWithLetter))
      } else
        Left(NotValidDni(Messages.prefixNotValidMsg))
    }

}
