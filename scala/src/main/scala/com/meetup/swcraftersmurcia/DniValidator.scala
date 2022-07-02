package com.meetup.swcraftersmurcia

import cats.data.{Validated, ValidatedNec}
import cats.syntax.all._
import com.meetup.swcraftersmurcia.DniError.{NotLastLetter, NotLastValidLetter, NotMatchingDigitControl, NotNineLong, NotValidPrefix}

object DniValidator {

  case class Dni(value: String)

  private def validateDocument[E <: DniError](
      mustBe: String => Boolean,
      error: E
  )(input: String): ValidatedNec[E, String] =
    Validated.cond(mustBe(input), input, error).toValidatedNec

  private def prefixOf(input: String): String =
    input.take(Constants.DNI_PREFIX_LENGTH)

  private def digitControlFor(prefix: String): Option[Char] = for {
    prefixNumber <- prefix.toLongOption
    control      <- ControlDigit.remainder.get(prefixNumber % Constants.DNI_DIGIT_CONTROL_REMAINDER)
  } yield control

  val nineCharsLongRule: String => Boolean   = _.length == Constants.DNI_LENGTH
  val prefixAllDigitsRule: String => Boolean = prefixOf(_).forall(_.isDigit)
  val lastCharNotNumRule: String => Boolean  = !_.last.isDigit
  val lastCharValidLetterRule: String => Boolean = input =>
    !Constants.INVALID_LAST_LETTERS.contains(input.last)
  val digitControlMatchingRule: String => Boolean = input =>
    digitControlFor(prefixOf(input)).contains(input.last)

  def validateDNI(rawInput: String): ValidatedNec[DniError, Dni] =
    validateDocument(nineCharsLongRule, NotNineLong)(rawInput)
      .andThen(validateDocument(prefixAllDigitsRule, NotValidPrefix))
      .andThen(validateDocument(lastCharNotNumRule, NotLastLetter))
      .andThen(validateDocument(lastCharValidLetterRule, NotLastValidLetter))
      .andThen(validateDocument(digitControlMatchingRule, NotMatchingDigitControl))
      .map(Dni)
}
