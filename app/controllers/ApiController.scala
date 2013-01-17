package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.libs.json._

import models.Member

object ApiController extends Controller {
  def exist(category: String, name: String) = Action {
    if (category == "uname") {
      Member.selectByUname(name) match {
        case Some(member) => Ok(Json.toJson(Map("exist" -> 1)))
        case None => Ok(Json.toJson(Map("exist" -> 0)))
      }
    } else if (category == "email") {
      Ok(Json.toJson("まだ実装してない..."))
    } else {
      Ok(Json.toJson("まだ実装してない..."))
    }
  }
}
