package controllers

import play.api.mvc.{Controller, Action}
import play.api.data.Form
import play.api.data.Forms._
import play.api.Play.current
import play.api.libs.json.Json
import play.Play
import sys.runtime

import library.Random
import models.Pass
import models.Member
import views.html

object PassController extends Controller {
  val passForm = Form(
      tuple(
        "token" -> text,
        "email" -> email, // FIXME (the validation is not so good)
        "password" -> nonEmptyText(minLength = 8)
        ))

  def resetRequest = Action {
    implicit request => {
      Ok(html.passResetRequest(request.session.get("uname")))
    }
  }

  def resetRequestPost = Action {
    implicit request => {
      try {
        val (_, email, _) = passForm.bindFromRequest.get
        val token = Random.randomAlphanumeriString(127)
        Pass.addOnetimeToken(email, token)
        runtime.exec(Play.application.path + "/script/password-reset.py %s %s" format (email, token))
        Ok(Json.toJson(Map(
            "success" -> Json.toJson(1),
            "message" -> Json.toJson("パスワード再設定依頼が完了しました")
            )))
      } catch {
        case e: Exception => {
          BadRequest(Json.toJson(Map(
            "success" -> Json.toJson(0),
            "message" -> Json.toJson("予期しないエラー " + e))
            ))
        }
      }
    }
  }

  def reset(token: String) = Action {
    implicit request => {
      val validity = Pass.checkTokenValidity(token)

      validity("isValid") match {
        case true => Ok(html.passReset(
          request.session.get("uname"),
          passForm.bind(Map(
            "token" -> token,
            "email" -> validity("email").toString
          )),
          validity))
        case false => Ok(html.passResetFailure(request.session.get("uname"), validity("message").toString))
      }
    }
  }

  def resetPost = Action {
    implicit request => {
      try {
        val (token, email, password) = passForm.bindFromRequest.get
        Member.updatePassword(email, password)
        Pass.invalidateToken(token)
        Ok(Json.toJson(Map(
            "success" -> Json.toJson(1),
            "message" -> Json.toJson("パスワード再設定が完了しました")
            )))
      } catch {
        case e: Exception => {
          BadRequest(Json.toJson(Map(
            "success" -> Json.toJson(0),
            "message" -> Json.toJson("予期しないエラー " + e))
            ))
        }
      }
    }
  }
}
