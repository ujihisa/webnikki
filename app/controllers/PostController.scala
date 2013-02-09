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
          "content" -> text
          ))

  def index = Action {
    implicit request =>
      Ok(html.post(postForm.bind(Map("token" -> request.session.get("token").getOrElse(""))), None))
  }

  def indexPost = Action {
    implicit request => {
      val (token, title, content) = postForm.bindFromRequest.get
      try {
        if (token != (request.session.get("token").getOrElse(""))) throw new Exception("CSRFトークンが一致しません。")
        val member = Member.selectByUname(request.session.get("uname").getOrElse(""))
        val postTime = Post.create(member.get[Long]("id"), title, content)

        Ok(Json.toJson(Map(
            "success" -> Json.toJson(1),
            "entry-url" -> Json.toJson(createEntryUrl(request.session.get("uname").getOrElse(""), postTime))
            )))
      } catch {
        case e: Exception => BadRequest(Json.toJson(Map(
            "success" -> Json.toJson(0),
            "message" -> Json.toJson("予期しないエラー"))
            ))
      }
    }
  }

  private def createEntryUrl(uname: String, postTime: Long) = {
    "http://%s.%s%s/entry/%s" format
        (uname,
         Play.current.configuration.getString("service.domain").getOrElse(""),
         if (Play.current.configuration.getString("service.port").getOrElse("").isEmpty) {
           ""
         } else {
           ":" + Play.current.configuration.getString("service.port").get
         },
         postTime.toString)
  }
}
