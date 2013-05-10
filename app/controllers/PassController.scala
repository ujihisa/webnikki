package controllers

import play.api.mvc.{Controller, Action}
import play.api.data.Form
import play.api.data.Forms._
import play.api.Play.current
import play.Play
import views.html
import sys.runtime
import models.Pass
import library.Random

object PassController extends Controller {
  val passForm = Form(
      tuple(
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
    // TODO: token の妥当性の確認と対応する email の取得

    Ok(html.reset(passForm.bind(Map(
        "email" -> "test@example.com"
        ))))
  }

  def resetPost() = Action {
    Ok("Hey")
  }
}