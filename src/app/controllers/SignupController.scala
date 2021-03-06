package controllers

import play.api.mvc.{Controller, Action}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json

import scala.io.Source
import views.html
import models.Member

import library.Random

object SignupController extends Controller {
  val memberForm = Form(
    tuple(
      "uname" -> nonEmptyText,
      "email" -> email, // FIXME (the validation is not so good)
      "password" -> nonEmptyText(minLength = 8) // FIXME this should *not* be hard coded
      ))

  def termsOfService = Action {
    implicit request => {
      Ok(html.termsOfService(request.session.get("uname"), io.Source.fromFile("resource/terms-of-service-20130329.md").mkString))
    }
  }

  def index = Action {
    implicit request => {
      Ok(html.signup(request.session.get("uname"), memberForm))
    }
  }

  def indexPost = Action {
    implicit request => {
      memberForm.bindFromRequest.value map { case (uname, email, password) =>
        try {
          Member.create(uname, email, password)
          Ok(Json.toJson(Map("success" -> Json.toJson(1)))).withSession("uname" -> uname, "token" -> Random.randomAlphanumeriString(64))
        } catch {
          case e: Exception => BadRequest(Json.toJson(Map("success" -> Json.toJson(0), "message" -> Json.toJson("エラー: " + e))))
        }
      } getOrElse {
        BadRequest(Json.toJson(Map("success" -> Json.toJson(0), "message" -> Json.toJson("memberForm に関するエラー"))))
      }
    }
  }
}
