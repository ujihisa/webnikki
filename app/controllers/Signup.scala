package controllers

import play.api._
import play.api.db._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
// import models.Member
// import anorm.NotAssigned

object SignupController extends Controller {
  // http://dev.classmethod.jp/server-side/play-yabe-2/
  // val memberForm: Form[Member] = Form(
  //   mapping(
  //     "id" -> number,
  //     "uname" -> text(minLength = 1),
  //     "password" -> text(minLength = 1),
  //     "password_salt" -> text(minLength = 1),
  //     "email" -> email
  //   ) {
  //     // (uname, password, password_salt, email) => Member(NotAssigned, uname, password, password_salt, email)
  //     (id, uname, password, password_salt, email) => Member(id, uname, password, password_salt, email)
  //   } {
  //     member => Some(member.id, member.uname, member.password, member.password_salt, member.email)
  //   }
  // )

  val memberForm = Form(
    tuple(
      "uname" -> text,
      "password" -> text,
      "email" -> email
    )
  )

  def index = Action {
    Ok(views.html.signup("Hello, this is signup!"))
  }

  // def indexPost = Action {
  //   implicit request => {
  //     println(request.body.asFormUrlEncoded.get("uname"))
  //     println(request.body.asFormUrlEncoded.get("uname").toString())
  //     Ok("Postされた")
  //   }
  // }

  def indexPost = Action {
    implicit request => memberForm.bindFromRequest.fold(
      errors => {
        println(errors)
        println(errors.getClass)
        Ok("なんかエラー")
      }, value => {
        println(value)
        println(value.getClass)
        Ok("こっちに来てればとりあえずおｋ")
      }
    )
  }
}
