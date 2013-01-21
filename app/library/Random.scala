package library

object Random {
  val rand = new scala.util.Random
  val lowerLimit = 0x21
  val upperLimit = 0x7e

  def randomNonControllAsciiChar: Char = (rand.nextInt(upperLimit - lowerLimit + 1) + lowerLimit).toChar

  def randomString(length: Int): String = {
    def t(acc: String, remain: Int): String = {
      remain match {
        case 0 => acc
        case _ => t(acc + randomNonControllAsciiChar, remain - 1) 
      }
    }

    t("", length)
  }
}
