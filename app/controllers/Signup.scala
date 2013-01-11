package controllers

import play.api._
import play.api.mvc._

object Signup extends Controller {
  def index = Action {
    Ok(views.html.signup("Hello, this is signup!"))
  }

  def indexPost = Action {
    implicit request => {
      // println(request.headers)
      println(request.body.asFormUrlEncoded.get("uname"))
      println(request.body.asFormUrlEncoded.get("uname").toString())
      Ok("Postされた")
    }
  }
}
