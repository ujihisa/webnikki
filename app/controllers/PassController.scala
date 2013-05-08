package controllers

import play.api.mvc.{Controller, Action}
import models.Pass
import library.Random
import play.api.Play.current
import play.Play
import sys.runtime

object PassController extends Controller {
  def resetRequest(email: String) = Action {
    val token = Random.randomAlphanumeriString(127)
    Pass.addOnetimeToken(email, token)
    println(Play.application.path)
    println(runtime.exec("touch /tmp/just-a-test"))
    runtime.exec(Play.application.path + "/script/mandrill-sample.py nori@mahata.net")

    // そしてemail
    Ok("Hello: " + email)
  }

  def reset(token: String) = Action {
    Ok("Yo")
  }
}