package controllers

import play.api._
import play.api.mvc._
import library.Random

import views._

object IndexController extends Controller {
  def index = Action {
    implicit request => {
      println(Play.current.configuration.getString("service.domain"))
      Ok(html.index("Hello, this is index!", request.session.get("uname")))
    }
  }

  def test = Action {
    implicit request => {
      println(request)
      println(request.host)
      println(request.domain)
      Ok(html.index("Hello, this is..., whatever :-)", None))
    }
  }

  def test2 = Action {
    println(Random.randomAlphanumeriString(10))
    Ok("日本語")
  }

  def test3 = Action {
    Ok("session store").withSession(
      "sample-key" -> "sample-value"
    )
  }

  def test4 = Action {
    implicit request => {
      session.get("sample-key").map { value =>
        Ok(value)
      }.getOrElse {
        Ok("化ける")
      }
    }
  }

  def test5 = Action {
    implicit request => {
      println(request.session.get("login"))
      Ok("Hello" + request.session.get("login"))
    }
  }

  def getSubdomain(domain: String) = {
    domain.substring(0, domain.indexOf('.'))
  }
}
