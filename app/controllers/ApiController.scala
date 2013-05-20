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

  def css(purpose: String, name: String) = Action {
    // TODO: cssType は "list" ないし "page" のどちらか

    val memberId = Member.selectByUname(name).get.id
    Ok(Json.toJson(
        Map(
            "success" -> Json.toJson(1),
            "code" -> Json.toJson(CssCustom.loadCss(memberId, purpose))
        )))
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

      try {
        if (token != (request.session.get("token").getOrElse(""))) throw new Exception("CSRFトークンが一致しません。")

        CssCustom.saveCss(memberId.toLong, purpose, text)
        Ok(Json.toJson(Map("success" -> Json.toJson(1))))
      } catch {
        case e: Exception =>
          BadRequest(Json.toJson(Map("success" -> Json.toJson(0), "message" -> Json.toJson("エラー: " + e))))
      }
    }
  }

  def js(purpose: String, name: String) = TODO
  def jsPost = TODO
}
