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
import library.Random

object PassController extends Controller {
  val passForm = Form(
      tuple(
        "token" -> text,
        "email" -> email, // FIXME (the validation is not so good)
        "password" -> nonEmptyText(minLength = 8)
        ))

  def resetRequest(email: String) = Action {
    val token = Random.randomAlphanumeriString(127)
    Pass.addOnetimeToken(email, token)
    println(Play.application.path + "/script/password-reset.py %s %s" format (email, token))
    runtime.exec(Play.application.path + "/script/password-reset.py %s %s" format (email, token))

    // そしてemail
    Ok("Hello: " + email)
  }

  def reset(token: String) = Action {
    println("reset controller")
    val validity = Pass.checkTokenValidity(token)

    println(validity, validity("isValid"))
    validity("isValid") match {
      case true => Ok(html.pass(
        passForm.bind(Map(
          "token" -> token,
          "email" -> "test@example.com"
        )),
        validity))
      case false => Ok(html.passFailure(validity("message").toString))
    }
  }

  def resetPost = Action {
    implicit request => {
      try {
        val (token, email, password) = passForm.bindFromRequest.get
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