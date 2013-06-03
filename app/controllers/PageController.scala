package controllers

import play.api.mvc.{Controller, Action}

import library.Util
import models.Member
import models.Post
import models.CustomData
import views.html

object PageController extends Controller {
  def index(pageNum: String) = Action {
    implicit request => {
      val memberId = Member.selectByUname(Util.getUnameFromSubdomain(request.domain)).get.id
      val posts = Post.postsByMemberId(memberId, Some(pageNum.toInt * Post.articlesPerPage))
      val css = CustomData.loadCss(memberId, "list")
      val js = CustomData.loadJs(memberId, "list")

      Ok(html.myindex(
        request.session.get("uname"),
        css,
        js,
        Util.getUnameFromSubdomain(request.domain),
        Post.postsByMemberId(memberId, Some(pageNum.toInt * Post.articlesPerPage))))
    }
  }
}

