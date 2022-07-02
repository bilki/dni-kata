package com.meetup.swcraftersmurcia

sealed abstract class DniError

object DniError {

  case object NotNineLong extends DniError
  type NotNineLong = NotNineLong.type

  case object NotLastLetter extends DniError
  type NotLastLetter = NotLastLetter.type

}
