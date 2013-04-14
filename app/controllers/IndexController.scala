package controllers

import play.api._
import play.api.mvc._
import library.Random
import library.Util

import views._
import models.Member
import models.Post

object IndexController extends Controller {
  def index = Action {
    implicit request => {
      Play.current.configuration.getString("service.domain") match {
        case Some(x) if request.domain.startsWith(x) =>
          Ok(html.index("トップページ", request.session.get("uname")))
        case _ => {
          val memberId = Member.selectByUname(Util.getUnameFromSubdomain(request.domain)).get.id
          Ok(html.myindex("各人のページ", request.session.get("uname"), Post.postsByMemberId(memberId)))
        }
      }
    }
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

  def ssl = Action {
    Ok("p0NkXRtbwO5PvM4q1k1a")
  }

  def getSubdomain(domain: String) = {
    domain.substring(0, domain.indexOf('.'))
  }
}
