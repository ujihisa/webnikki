package controllers

import play.api.mvc.{Controller, Action}
import play.api.data.Form
import play.api.data.Forms._
import play.api.Play.current
import play.Play
import play.api.libs.json.Json
import views.html
import sys.runtime
import models.Pass
import models.Member
import library.Random

object PassController extends Controller {
  val passForm = Form(
      tuple(
        "token" -> text,
        "email" -> email, // FIXME (the validation is not so good)
        "password" -> nonEmptyText(minLength = 8)
        ))

  def resetRequest = Action {
    Ok(html.passResetRequest)
  }

  def resetRequestPost = Action {
//    val token = Random.randomAlphanumeriString(127)
//    Pass.addOnetimeToken(email, token)
//    runtime.exec(Play.application.path + "/script/password-reset.py %s %s" format (email, token))
    implicit request => {
      println(request.queryString)
    }
  }

  def reset(token: String) = Action {
    val validity = Pass.checkTokenValidity(token)

    validity("isValid") match {
      case true => Ok(html.passReset(
        passForm.bind(Map(
          "token" -> token,
          "email" -> validity("email").toString
        )),
        validity))
      case false => Ok(html.passResetFailure(validity("message").toString))
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
            "message" -> Json.toJson("パスワードの再設定が完了しました")
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