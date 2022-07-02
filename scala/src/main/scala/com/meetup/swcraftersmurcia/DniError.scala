package com.meetup.swcraftersmurcia

sealed abstract class DniError

object DniError {

  case object NotNineLong extends DniError
  type NotNineLong = NotNineLong.type

  case object NotLastLetter extends DniError
  type NotLastLetter = NotLastLetter.type

  case object NotLastValidLetter extends DniError
  type NotLastValidLetter = NotLastValidLetter.type

  case object NotValidPrefix extends DniError
  type NotValidPrefix = NotValidPrefix.type

  case object NotMatchingDigitControl extends DniError
  type NotMatchingDigitControl = NotMatchingDigitControl.type

}
