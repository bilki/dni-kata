package com.meetup.swcraftersmurcia

import org.scalacheck.Gen

object Generators {

  val notNineCharsGen: Gen[String] = Gen.alphaNumStr.filterNot(_.length == Constants.DNI_LENGTH)

}
