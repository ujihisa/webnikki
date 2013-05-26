package controllers

import play.api.Play.current
import play.api.mvc.{Controller, Action}
import library.Random
import library.Util

import views.html
import models.Member
import models.Post
import models.CustomData

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
          Ok(html.myindex(request.session.get("uname"), css, js, Util.getUnameFromSubdomain(request.domain), Post.postsByMemberId(memberId)))
        }
      }
    }
  }

  def test = Action {
    implicit request => {
      Ok(html.test(request.session.get("uname")))
    }
  }

  def testPost = Action(parse.multipartFormData) {
    implicit request => {
      request.body.file("file").map { picture =>
        import java.io.File
        val filename = picture.filename 
        val contentType = picture.contentType
        picture.ref.moveTo(new File("/tmp/picture"))
        Ok("File uploaded")
      }.getOrElse {
        BadRequest("File upload failed")
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
