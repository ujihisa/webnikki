package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._
import models.Member

import library.Random

object LoginController extends Controller {
  val memberForm = Form(
    tuple(
        "email" -> email,
        "password" -> nonEmptyText(minLength = 4)
        ))

  def index = Action {
    Ok(html.login(memberForm, None))
  }

  def indexPost = Action {
    implicit request =>
      memberForm.bindFromRequest.fold(
          formWithErrors => {
            BadRequest(html.login(formWithErrors, Some("メールアドレスかパスワードを間違えちゃってるみたいです...。")))
          }, {
            case(email, password) => {
              if (Member.isValidPassword(email, password)) {
                val member = Member.selectByEmail(email)
                Ok("それ正しいパスワードだよ! おめでとう!").withSession("uname" -> member.get[String]("uname"), "token" -> Random.randomAlphanumeriString(64))
              } else {
                BadRequest(
                    html.login(
                      memberForm.bind(Map("email" -> email, "password" -> password)),
                      Some("パスワードを間違えちゃってるみたいです...。")))
              }
            }
          })
  }
}
