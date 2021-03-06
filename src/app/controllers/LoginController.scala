package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json

import views._
import models.Member

import library.Random

object LoginController extends Controller {
  val memberForm = Form(
    tuple(
        "email" -> text,
        "password" -> text
        ))

  def index = Action {
    implicit request => {
      Ok(html.login(request.session.get("uname"), memberForm, None))
    }
  }

  def indexPost = Action {
    implicit request => {
      memberForm.bindFromRequest.value map { case (email, password) =>
        try {
          if (Member.isValidPassword(email, password)) {
            val member = Member.selectByEmail(email)
            Ok(Json.toJson(Map("success" -> Json.toJson(1)))).withSession("uname" -> member.get.uname, "token" -> Random.randomAlphanumeriString(64))
          } else {
            Ok(Json.toJson(Map("success" -> Json.toJson(0))))
          }
        } catch {
          case e: Exception => BadRequest(Json.toJson(Map("success" -> Json.toJson(0), "message" -> Json.toJson("エラー: " + e))))
        }
      } getOrElse {
        BadRequest(Json.toJson(Map("success" -> Json.toJson(0), "message" -> Json.toJson("エラー: form is ヤバい"))))
      }
    }
  }
}
