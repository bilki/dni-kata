package com.meetup.swcraftersmurcia

import scala.io.StdIn

object Main extends App {

  print("Input your DNI: ")
  val input = StdIn.readLine

  val result = DniValidator.validateDNI(input)

  result.fold(
    err => println(s"DNI validation failed with [${err.msg}]"),
    _ => println("DNI validation successful")
  )
}
