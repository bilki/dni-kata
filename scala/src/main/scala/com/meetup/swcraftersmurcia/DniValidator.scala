package com.meetup.swcraftersmurcia

import cats.syntax.all._
import com.meetup.swcraftersmurcia.DniError._

object DniValidator {

  case class Dni(value: String)

  private def validateDocument[E <: DniError](
      mustBe: String => Boolean,
      error: E,
      input: String
  ): Either[E, String] =
    Either.cond(mustBe(input), input, error)

  private def prefixOf(input: String): String =
    input.take(Constants.DNI_PREFIX_LENGTH)

  private def digitControlFor(prefix: String): Option[Char] = for {
    prefixNumber <- prefix.toLongOption
    remainderIdx = prefixNumber % Constants.DNI_DIGIT_CONTROL_REMAINDER
    control <- ControlDigit.remainder.get(remainderIdx)
  } yield control

  val nineCharsLongRule: String => Boolean   = _.length == Constants.DNI_LENGTH
  val prefixAllDigitsRule: String => Boolean = prefixOf(_).forall(_.isDigit)
  val lastCharNotNumRule: String => Boolean  = !_.last.isDigit
  val lastCharValidLetterRule: String => Boolean = input =>
    !Constants.INVALID_LAST_LETTERS.contains(input.last)
  val digitControlMatchingRule: String => Boolean = input =>
    digitControlFor(prefixOf(input)).contains(input.last)

  def validateNIE(input: String): Either[NotFirstNieLetter, String] = {
    val maybeFirstChar = input.headOption

    if (maybeFirstChar.exists(_.isDigit))
      input.asRight
    else
      maybeFirstChar
        .flatMap(NieSpecific.replaceDigit.get)
        .fold(NotFirstNieLetter.asLeft[String])(replacement =>
          s"${replacement}${input.tail}".asRight
        )
  }

  def validateDNI(rawInput: String): Either[DniError, Dni] =
    for {
      _           <- validateDocument(nineCharsLongRule, NotNineLong, rawInput)
      replacedNIE <- validateNIE(rawInput)
      _ <- validateDocument(prefixAllDigitsRule, NotValidPrefix, replacedNIE)
      _ <- validateDocument(lastCharNotNumRule, NotLastLetter, replacedNIE)
      _ <- validateDocument(
        lastCharValidLetterRule,
        NotLastValidLetter,
        replacedNIE
      )
      _ <- validateDocument(
        digitControlMatchingRule,
        NotMatchingDigitControl,
        replacedNIE
      )
    } yield Dni(rawInput)
}
