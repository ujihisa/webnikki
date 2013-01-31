package library

object Util {
  def getUnameFromSubdomain(subdomain: String) = subdomain.split('.')(0)
}