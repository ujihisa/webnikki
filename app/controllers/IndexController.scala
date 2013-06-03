package controllers

import play.api.Play.current
import play.api.mvc.{Controller, Action}
import play.api.libs.json.Json

import library.Random
import library.Util
import models.Member
import models.Post
import models.CustomData
import views.html

object IndexController extends Controller {
  def index = Action {
    implicit request => {
      current.configuration.getString("service.domain") match {
        case Some(x) if request.domain.startsWith(x) =>
          Ok(html.index(request.session.get("uname")))
        case _ => {
          val memberId = Member.selectByUname(Util.getUnameFromSubdomain(request.domain)).get.id
          val css = CustomData.loadCss(memberId, "list")
          val js = CustomData.loadJs(memberId, "list")
          // println("counts", Post.countPostsByMemberId(memberId))
          Ok(html.myindex(
            request.session.get("uname"),
            css,
            js,
            Util.getUnameFromSubdomain(request.domain),
            Post.postsByMemberId(memberId, Some(0)),
            1,
            Map(
              "prev" -> false,
              "next" -> Post.isNextPage(memberId, 1)))
          )
        }
      }
    }
  }

  def robots = Action {
    val robotsTxt =
      "User-agent: *\n" +
      "Disallow: /login\n" +
      "Disallow: /signup\n" +
      "Disallow: /manage\n" +
      "Disallow: /rss\n"
    Ok(robotsTxt)
  }
}
