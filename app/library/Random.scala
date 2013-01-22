package library

object Random {
  def randomString(length: Int) = {
    Stream.continually(util.Random.nextPrintableChar) take length mkString
  }
}
