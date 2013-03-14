package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._

object LogoutController extends Controller {
  def index = Action {
    Ok(html.logout("ログアウト")).withNewSession
  }
}