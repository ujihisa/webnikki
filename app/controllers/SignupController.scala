package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._
import models.Member

object SignupController extends Controller {
  val memberForm = Form(
    tuple(
      "uname" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText(minLength = 4) // FIXME this should *not* be hard coded
      ))

  def index = Action {
    Ok(html.signup(memberForm, None))
  }

  def indexPost = Action {
    implicit request =>
      memberForm.bindFromRequest.fold(
        formWithErrors => {
          BadRequest(html.signup(formWithErrors, None))
        }, {
          case (uname, email, password) => {
            try {
              Member.create(uname, email, password)
              Ok(html.signupSuccess(""))
            } catch {
              case e: Exception => {
                BadRequest(
                  html.signup(
                    memberForm.bind(Map("uname" -> uname, "email" -> email, "password" -> password)),
                    Some("ごめんなさい...ユーザ名かメールアドレスが既に使われているみたいです...。")))
              }
            }
          }
        })
  }
}
