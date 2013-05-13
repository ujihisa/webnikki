package controllers

import play.api.Play.current
import play.api.mvc.{Controller, Action}
import library.Random
import library.Util

import views.html
import models.Member
import models.Post

object IndexController extends Controller {
  def index = Action {
    implicit request => {
      current.configuration.getString("service.domain") match {
        case Some(x) if request.domain.startsWith(x) =>
          Ok(html.index("トップ - web日記", request.session.get("uname")))
        case _ => {
          val memberId = Member.selectByUname(Util.getUnameFromSubdomain(request.domain)).get.id
          Ok(html.myindex("ユーザーページ - web日記", Util.getUnameFromSubdomain(request.domain), Post.postsByMemberId(memberId)))
        }
      }
    }
  }

  def robots = Action {
    val robotsTxt =
      "User-agent: *\n" +
      "Disallow: /login\n" +
      "Disallow: /signup\n" +
      "Disallow: /manage\n"
    Ok(robotsTxt)
  }

  def test = Action {
    implicit request => {
      Ok(html.test("Hello, this is..., whatever :-)"))
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
