package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json

import views._
import models.Member
import models.Post

object PostController extends Controller {
  val postForm = Form(
      tuple(
          "token" -> text,
          "title" -> text,
          "content" -> text,
          "created_at" -> text
          ))

  def index = Action {
    implicit request =>
      Ok(html.post(postForm.bind(Map("token" -> request.session.get("token").getOrElse("")))))
  }

  def indexEdit(createdAt: String) = Action {
    implicit request => {
      val member = Member.selectByUname(request.session.get("uname").getOrElse(""))

      val post = Post.postByMemberIdAndCreatedAt(member.get.id, createdAt.toLong).get
      Ok(html.post(postForm.bind(Map(
          "token" -> request.session.get("token").getOrElse(""),
          "title" -> post.title,
          "content" -> post.content,
          "created_at" -> post.created_at.toString
          ))))
    }
  }

  def indexPost = Action {
    implicit request => {
      try {
        val (token, title, content, created_at) = postForm.bindFromRequest.get
        if (token != (request.session.get("token").getOrElse(""))) throw new Exception("CSRFトークンが一致しません。")
        val member = Member.selectByUname(request.session.get("uname").getOrElse(""))
        val _created_at = if (created_at.isEmpty) {
          Post.create(member.get.id, title, content)
        } else {
          Post.update(member.get.id, title, content, created_at.toLong)
        }

        Ok(Json.toJson(Map(
            "success" -> Json.toJson(1),
            "created_at" -> Json.toJson(_created_at),
            "url" -> Json.toJson(createEntryUrl(request.session.get("uname").getOrElse(""), _created_at))
            )))
      } catch {
        case e: Exception => BadRequest(Json.toJson(Map(
            "success" -> Json.toJson(0),
            "message" -> Json.toJson("予期しないエラー " + e))
            ))
      }
    }
  }

  private def createEntryUrl(uname: String, createdAt: Long) = {
    "http://%s.%s%s/entry/%s" format
        (uname,
         Play.current.configuration.getString("service.domain").getOrElse(""),
         Play.current.configuration.getString("service.port").map { x => ":%s" format x }.getOrElse(""),
         createdAt.toString)
  }
}
