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
          Ok(html.index(request.session.get("uname")))
        case _ => {
          val memberId = Member.selectByUname(Util.getUnameFromSubdomain(request.domain)).get.id
          Ok(html.myindex(request.session.get("uname"), Util.getUnameFromSubdomain(request.domain), Post.postsByMemberId(memberId)))
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
}
