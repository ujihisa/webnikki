package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json

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
      val memberId = Member.selectByUname(Util.getUnameFromSubdomain(request.domain)).get.id
      Post.postByMemberIdAndCreatedAt(memberId, createdAt.toLong) match {
        case Some(entry) => Ok(html.entry("", entry, Comment.commentsByPostId(entry.id)))
        case None => BadRequest("その記事存在しないです...")
      }
    }
  }

  def indexPost = Action {
    implicit request => {
//      val (post_id, uname, content) = commentForm.bindFromRequest().get
//      try {
//        Comment.create(post_id, 0, uname, content, true)
//        Ok("ポストされると、ここにリクエストくるはず。")
//      } catch {
//        case e: Exception => BadRequest(s"エラー: $e")
//      }
      commentForm.bindFromRequest.value map { case(post_id, uname, content) =>
        try {
          val created_at = Comment.create(post_id, 0, uname, content, true)
          Ok(Json.toJson(Map(
              "success" -> Json.toJson(1),
              "created_at" -> Json.toJson(created_at)
              )))
        } catch {
          case e: Exception => BadRequest(Json.toJson(Map(
              "success" -> Json.toJson(0),
              "message" -> Json.toJson("エラー: " + e)
              )))
        }
      } getOrElse {
        BadRequest(Json.toJson(Map("success" -> Json.toJson(0), "message" -> Json.toJson("commentForm に関するエラー"))))
      }
    }
  }
}
