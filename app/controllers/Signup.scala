package controllers

import play.api._
import play.api.mvc._

object Signup extends Controller {
  def index = Action {
    Ok(views.html.signup("Hello, this is signup!"))
  }
}
