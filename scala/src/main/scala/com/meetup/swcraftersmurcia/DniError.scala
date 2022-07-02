package com.meetup.swcraftersmurcia

sealed abstract class DniError(val msg: String)

object DniError {

  case object NotNineLong extends DniError("Input must be 9 chars long")
  type NotNineLong = NotNineLong.type

  case object NotLastLetter extends DniError("Input must end with a letter")
  type NotLastLetter = NotLastLetter.type

  case object NotLastValidLetter extends DniError("Input must not end with U, I, O or Ñ")
  type NotLastValidLetter = NotLastValidLetter.type

  case object NotValidPrefix extends DniError("Input must start with 8 digits")
  type NotValidPrefix = NotValidPrefix.type

  case object NotMatchingDigitControl extends DniError("Input must end with the correct control letter")
  type NotMatchingDigitControl = NotMatchingDigitControl.type

  case object NotFirstNieLetter extends DniError("Input starting with letter not valid for NIE")
  type NotFirstNieLetter = NotFirstNieLetter.type
}
