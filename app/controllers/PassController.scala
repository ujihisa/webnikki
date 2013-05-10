package controllers

import play.api.mvc.{Controller, Action}
import play.api.Play.current
import play.Play
import views.html
import sys.runtime
import models.Pass
import library.Random

object PassController extends Controller {
  def resetRequest(email: String) = Action {
    val token = Random.randomAlphanumeriString(127)
    Pass.addOnetimeToken(email, token)
    println(Play.application.path + "/script/password-reset.py %s %s" format (email, token))
    runtime.exec(Play.application.path + "/script/password-reset.py %s %s" format (email, token))

    // そしてemail
    Ok("Hello: " + email)
  }

  def reset(token: String) = Action {
    Ok(html.reset())
  }

  def resetPost() = Action {
    Ok("Hey")
  }
}