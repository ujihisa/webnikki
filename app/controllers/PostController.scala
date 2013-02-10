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
      Ok(html.post(postForm.bind(Map("token" -> request.session.get("token").getOrElse(""))), None))
  }

  def indexPost = Action {
    implicit request => {
      try {
        val (token, title, content, created_at) = postForm.bindFromRequest.get
        if (token != (request.session.get("token").getOrElse(""))) throw new Exception("CSRFトークンが一致しません。")
        val member = Member.selectByUname(request.session.get("uname").getOrElse(""))
        val _created_at = if (created_at.isEmpty) {
          Post.create(member.get[Long]("id"), title, content)
        } else {
          Post.update(member.get[Long]("id"), title, content, created_at.toLong)
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

  private def createEntryUrl(uname: String, created_at: Long) = {
    "http://%s.%s%s/entry/%s" format
        (uname,
         Play.current.configuration.getString("service.domain").getOrElse(""),
         if (Play.current.configuration.getString("service.port").getOrElse("").isEmpty) {
           ""
         } else {
           ":" + Play.current.configuration.getString("service.port").get
         },
         created_at.toString)
  }
}
