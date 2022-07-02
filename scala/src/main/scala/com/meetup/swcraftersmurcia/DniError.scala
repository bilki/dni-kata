package com.meetup.swcraftersmurcia

sealed abstract class DniError

object DniError {

  case object NotNineLong extends DniError

}
