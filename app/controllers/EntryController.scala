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
  val entryForm = Form(
      tuple(
          "post_id" -> number,
          "uname" -> text,
          "content" -> text
          ))

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

  def indexPost = Action {
    implicit request => {
      val (post_id, uname, content) = entryForm.bindFromRequest().get
      try {
        // TODO コメントをDBにツッコむ
        println(post_id)
        println(uname)
        println(content)
        Ok("ポストされると、ここにリクエストくるはず。")
      } catch {
        case e: Exception => BadRequest("エラー: " + e)
      }
    }
  }
}