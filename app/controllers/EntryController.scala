package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import library.Util

import views._
import models.Member
import models.Post

object EntryController extends Controller {
  def index(createdAt: String) = Action {
    implicit request => {
      val memberId = Member.selectByUname(Util.getUnameFromSubdomain(request.domain)).get[Long]("id")
      val entry = Post.postByMemberIdAndCreatedAt(memberId, createdAt.toLong)
      
      entry match {
        case Some(entry) => Ok(html.entry("", entry))
        case None => BadRequest("その記事存在しないです...")
      }
    }
  }
}