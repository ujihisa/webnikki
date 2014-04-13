package library

object Random {
  def randomAlphanumeriString(length: Int) = scala.util.Random.alphanumeric take length mkString
  def randomPrintableString(length: Int) = Stream.continually(util.Random.nextPrintableChar) take length mkString
}
