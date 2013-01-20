package library

import scala.collection.immutable.StringOps
// import scala.util.Random

object Random {
  val rand = new scala.util.Random
  val lowerLimit = 0x21
  val upperLimit = 0x7e

  def randomNonControllAsciiChar: Char = (rand.nextInt(upperLimit - lowerLimit + 1) + lowerLimit).toChar

  def randomString(length: Int): String = {
    def t(acc: StringOps, remain: Int): StringOps = {
      remain match {
        case 0 => acc
        case _ => t(acc + randomNonControllAsciiChar, remain - 1) 
      }
//      if (0 < remain)
//        t(acc + randomNonControllAsciiChar, remain - 1)
//      else
//        acc
    }

    t(new StringOps(""), length).toString
  }
}
