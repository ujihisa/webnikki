package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json

import views._
import models.Member

import library.Random

object SignupController extends Controller {
  val memberForm = Form(
    tuple(
      "uname" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText(minLength = 4) // FIXME this should *not* be hard coded
      ))

  def index = Action {
    Ok(html.signup(memberForm, None))
  }

  def indexPost = Action {
    implicit request => {
      val (uname, email, password) = memberForm.bindFromRequest.get
      try {
        Member.create(uname, email, password)
        Ok(Json.toJson(Map("success" -> Json.toJson(1)))).withSession("uname" -> uname, "token" -> Random.randomAlphanumeriString(64))
      } catch {
        case e: Exception => BadRequest(Json.toJson(Map("success" -> Json.toJson(0), "message" -> Json.toJson("エラー: " + e))))
      }
    }
  }
}
