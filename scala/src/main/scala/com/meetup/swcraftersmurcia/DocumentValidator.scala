package com.meetup.swcraftersmurcia

object DocumentValidator {

  object Messages {

    val sizeNotValidMsg = "size not equal to 9"
    val prefixNotValidMsg = "dni prefix should be all numbers"
    val endsWithInvalidChar = "dni should end with a letter"
    val controlSumDigitNotValid = "control digit not valid"
    val nieFirstLetterNotValid = "nie letter not XYZ"

  }

  sealed abstract class IdentityDoc(value: String)

  case class Dni(value: String) extends IdentityDoc(value)

  case class Nie(value: String) extends IdentityDoc(value)

  sealed abstract class NotValidDocument(msg: String)

  case class NotValidDni(msg: String) extends NotValidDocument(msg)

  val controlDigitModule = 23

  case class NotValidNie(msg: String) extends NotValidDocument(msg)

  private def validateDoc(rawInput: String): Either[NotValidDni, IdentityDoc] =
    if (rawInput.length != 9) Left(NotValidDni(Messages.sizeNotValidMsg))
    else {
      val prefix = rawInput.take(8)
      if (prefix.forall(_.isDigit)) {
        val lastLetter = rawInput.reverse.head
        if (Remainder.letter.values.toList.contains(lastLetter)) {
          val remainder = prefix.toLong % controlDigitModule
          if (Remainder.letter.get(remainder).contains(lastLetter))
            Right(Dni(rawInput))
          else
            Left(NotValidDni(Messages.controlSumDigitNotValid))
        } else
          Left(NotValidDni(Messages.endsWithInvalidChar))
      } else
        Left(NotValidDni(Messages.prefixNotValidMsg))
    }

  def validate(rawInput: String): Either[NotValidDocument, IdentityDoc] = {
    val firstChar = rawInput.head
    if (Remainder.nieDigits.contains(firstChar)) {
      val replacement = Remainder.nieDigits(firstChar)
      val newInput = s"${replacement}${rawInput.tail}"
      validateDoc(newInput)
    } else validateDoc(rawInput)
  }

}
