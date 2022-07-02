package com.meetup.swcraftersmurcia

import org.scalacheck.Gen

object Generators {

  val notNineCharsGen: Gen[String] =
    Gen.alphaNumStr.filterNot(_.length == Constants.DNI_LENGTH)

  val validPrefixGen: Gen[String] =
    Gen.listOfN(Constants.DNI_PREFIX_LENGTH, Gen.numChar).map(_.mkString)

  val notLastCharWithNumGen: Gen[String] =
    for {
      prefix <- validPrefixGen
      last   <- Gen.numChar
    } yield s"${prefix}${last}"

  val invalidLastCharGen: Gen[Char] = Gen.oneOf(Constants.INVALID_LAST_LETTERS)

  val validLastCharGen: Gen[Char] =
    Gen.alphaChar.filterNot(Constants.INVALID_LAST_LETTERS.contains)

  val notLastCharWithForbiddenGen: Gen[String] =
    for {
      prefix <- validPrefixGen
      last   <- invalidLastCharGen
    } yield s"${prefix}${last}"

  val invalidPrefixGen: Gen[String] = Gen
    .listOfN(Constants.DNI_PREFIX_LENGTH, Gen.alphaNumChar)
    .filterNot(_.exists(_.isDigit))
    .map(_.mkString)

  val notAllDigitsPrefixGen: Gen[String] =
    for {
      invalidPrefix <- invalidPrefixGen
      last          <- validLastCharGen
    } yield s"${invalidPrefix}${last}"

  val allPossibleCombinationsGen: Gen[List[String]] = {
    val digitControlChars = ControlDigit.remainder.values.toList
    validPrefixGen.map(prefix =>
      digitControlChars.map(digitControl => s"${prefix}${digitControl}")
    )
  }
}
