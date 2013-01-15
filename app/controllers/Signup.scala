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
      "password" -> nonEmptyText,
      "email" -> email
    )
  )

  def index = Action {
    Ok(html.signup(memberForm))
  }

  def indexPost = Action {
    implicit request => memberForm.bindFromRequest.fold(
      formWithErrors => {
        println(formWithErrors)
        BadRequest(html.signup(formWithErrors))
      }, {
        case (uname, password, email) => {
          try {
            Member.create(uname, password, email)
            Ok(html.signupSuccess(""))
          } catch {
            case e: Exception => {
              println("exception caught: " + e.getMessage)
              BadRequest(html.signup(memberForm)) // 2nd argumentにOption型のエラーメッセージ付ける
            }
          }
        }
      }
    )
  }
}
