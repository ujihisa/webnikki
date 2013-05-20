package controllers

import play.api.mvc.{Controller, Action}
import views.html

object ManageController extends Controller {
  def index = Action {
    implicit request =>
      Ok(html.manage(
          request.session.get("token").getOrElse(""),
          request.session.get("uname").getOrElse("")
          ))
  }
}