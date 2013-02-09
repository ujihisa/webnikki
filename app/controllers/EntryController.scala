package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import library.Util

import views._
import models.Member
import models.Post
import models.Comment

object EntryController extends Controller {
  val commentForm = Form(
      tuple(
          "post_id" -> number,
          "uname" -> text,
          "content" -> text
          ))

  def index(createdAt: String) = Action {
    implicit request => {
      val memberId = Member.selectByUname(Util.getUnameFromSubdomain(request.domain)).get[Long]("id")
      Post.postByMemberIdAndCreatedAt(memberId, createdAt.toLong) match {
        case Some(entry) => Ok(html.entry("", entry, Comment.commentsByPostId(entry.id)))
        case None => BadRequest("その記事存在しないです...")
      }
    }
  }

  def indexPost = Action {
    implicit request => {
      val (post_id, uname, content) = commentForm.bindFromRequest().get
      try {
        Comment.create(post_id, 0, uname, content, true)
        Ok("ポストされると、ここにリクエストくるはず。")
      } catch {
        case e: Exception => BadRequest(s"エラー: $e")
      }
    }
  }
}
