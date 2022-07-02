package com.meetup.swcraftersmurcia

import org.scalacheck.Gen

object Generators {

  val notNineCharsGen: Gen[String] = Gen.alphaNumStr.filterNot(_.length == Constants.DNI_LENGTH)

  val validPrefixGen: Gen[String] = Gen.listOfN(Constants.DNI_PREFIX_LENGTH, Gen.numChar).map(_.mkString)

  val notLastCharWithNumGen: Gen[String] =
    for {
      prefix <- validPrefixGen
      last   <- Gen.numChar
    } yield s"${prefix}${last}"

  val invalidLastCharGen: Gen[Char] = Gen.oneOf(Constants.INVALID_LAST_LETTERS)

  val validLastCharGen: Gen[Char] = Gen.alphaChar.filterNot(Constants.INVALID_LAST_LETTERS.contains)

  val notLastCharWithForbiddenGen: Gen[String] =
    for {
      prefix <- validPrefixGen
      last   <- invalidLastCharGen
    } yield s"${prefix}${last}"
}
