package com.meetup.swcraftersmurcia

import cats.data.{Validated, ValidatedNec}
import cats.syntax.all._
import com.meetup.swcraftersmurcia.DniError.{
  NotLastLetter,
  NotLastValidLetter,
  NotNineLong,
  NotValidPrefix
}

object DniValidator {

  case class Dni(value: String)

  private def validateDocument[E <: DniError](
      mustBe: String => Boolean,
      error: E
  )(input: String): ValidatedNec[E, String] =
    Validated.cond(mustBe(input), input, error).toValidatedNec

  val nineCharsLongRule: String => Boolean   = _.length == Constants.DNI_LENGTH
  val prefixAllDigitsRule: String => Boolean = _.take(Constants.DNI_PREFIX_LENGTH).forall(_.isDigit)
  val lastCharNotNumRule: String => Boolean  = !_.last.isDigit
  val lastCharValidLetterRule: String => Boolean = input =>
    !Constants.INVALID_LAST_LETTERS.contains(input.last)

  def validateDNI(rawInput: String): ValidatedNec[DniError, Dni] =
    validateDocument(nineCharsLongRule, NotNineLong)(rawInput)
      .andThen(validateDocument(prefixAllDigitsRule, NotValidPrefix))
      .andThen(validateDocument(lastCharNotNumRule, NotLastLetter))
      .andThen(validateDocument(lastCharValidLetterRule, NotLastValidLetter))
      .map(Dni)

}
