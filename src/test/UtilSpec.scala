package test

import org.specs2.mutable._
import library.Util

class UtilSpec extends Specification {
  "Util.getUnameFromPath" should {
    "trim username from access path (i.e. /user)" in {
      Util.getUnameFromPath("/user") must equalTo("user")
      Util.getUnameFromPath("/user/") must equalTo("user")
      Util.getUnameFromPath("/user/dummy") must equalTo("user")
      Util.getUnameFromPath("//") must equalTo("")
      Util.getUnameFromPath("/") must equalTo("")
      Util.getUnameFromPath("") must equalTo("")
    }
  }
}