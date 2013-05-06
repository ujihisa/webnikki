package controllers

import play.api.mvc.{Controller, Action}

object PassController extends Controller {
  def resetRequest(email: String) = Action {
    Ok("Hello: " + email)
  }

  def reset(token: String) = Action {
    Ok("Yo")
  }
}