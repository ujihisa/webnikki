package controllers

import play.api._
import play.api.mvc._

object Index extends Controller {
  def index = Action {
    Ok(views.html.index("Hello, this is index!"))
  }

  def test = Action {
    implicit request => {
      println(request)
      println(request.host)
      println(request.domain)
      Ok(views.html.index("Hello, this is..., whatever :-)"))
    }
  }

  def test2 = Action {
    Ok("天才のそれに近いな。")
  }

  def test3 = Action {
    Ok("session store").withSession(
      "sample-key" -> "sample-value"
    )
  }

  // http://dev.classmethod.jp/server-side/play20-extcontroller/
  def test4 = Action {
    implicit request => {
      session.get("sample-key").map { value =>
        Ok(value)
      }.getOrElse {
        Ok("が、ダメっ!")
      }
    }
  }

  def getSubdomain(domain: String) = {
    domain.substring(0, domain.indexOf('.'))
  }
}
