package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json

import com.tristanhunt.knockoff.DefaultDiscounter._

import scala.io.Source
import views._
import models.Member

import library.Random

object SignupController extends Controller {
  val memberForm = Form(
    tuple(
      "uname" -> nonEmptyText,
      "email" -> email, // FIXME (the validation is not so good)
      "password" -> nonEmptyText(minLength = 4) // FIXME this should *not* be hard coded
      ))

  def termsOfService = Action {
    val termsOfService = toXHTML(knockoff(io.Source.fromFile("resource/terms-of-service.md").mkString))
    Ok(html.termsOfService(termsOfService.toString))
  }

  def index = Action {
    Ok(html.signup(memberForm))
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
