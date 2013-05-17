package controllers

import play.api.mvc.{Controller, Action}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json

import models.Member
import models.CssCustom

object ApiController extends Controller {
  def exist(category: String, name: String) = Action {
    val x = category match {
      case "uname" => Right(Member.selectByUname(_))
      case "email" => Right(Member.selectByEmail(_))
      case _ => Left("no such request point.")
    }
    x.right.map { f =>
      f(name) match {
        case Some(member) => (Json.toJson(Map("exist" -> 1)))
        case None => (Json.toJson(Map("exist" -> 0)))
      }
    } match {
      case Right(json) => Ok(json)
      case Left(message) => BadRequest(Json.toJson(Map("error" -> message)))
    }
  }

  def css(purpose: String, memberId: String) = Action {
    // cssType は "list" ないし "page" のどちらか
    Ok("Hello")
  }

  val cssJsForm = Form(tuple(
      "token" -> text,
      "purpose" -> text,
      "text"  -> text
      ))
  def cssPost = Action {
    implicit request => {
      val (token, purpose, text) = cssJsForm.bindFromRequest.get
      val uname = request.session.get("uname").getOrElse("")
      val memberId = Member.selectByUname(uname).get.id

      // if (token != (request.session.get("token").getOrElse(""))) throw new Exception("CSRFトークンが一致しません。")

      CssCustom.saveCss(memberId.toLong, purpose, text)
      Ok("yo")
    }
  }

  def js(purpose: String, memberId: String) = TODO
  def jsPost = TODO
}
