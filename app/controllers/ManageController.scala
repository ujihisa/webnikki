package controllers

import play.api.mvc.{Controller, Action}
import views.html

object ManageController extends Controller {
  def index = Action {
    Ok(html.manage())
  }
}