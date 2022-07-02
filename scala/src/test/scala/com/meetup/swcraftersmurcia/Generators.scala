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

}
