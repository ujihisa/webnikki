package controllers

import play.api._
import play.api.mvc._

object IndexController extends Controller {
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
    Ok("日本語")
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
        Ok("化ける")
      }
    }
  }

  def getSubdomain(domain: String) = {
    domain.substring(0, domain.indexOf('.'))
  }
}
