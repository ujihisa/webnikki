package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._
// import models.Member

object LoginController extends Controller {
  val memberForm = Form(
    tuple(
      "email" -> email,
      "password" -> nonEmptyText(minLength = 4)
      ))

  def index = Action {
    Ok(views.html.login(memberForm, None))
  }

  def indexPost = TODO
}
