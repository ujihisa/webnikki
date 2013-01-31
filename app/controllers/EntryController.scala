package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._
import models.Member
import models.Post

object EntryController extends Controller {
  def index(createdAt: String) = Action {
    implicit request => {
      println(Play.current.configuration.getString("service.domain").getOrElse(""))
      println(request.domain)
      Ok("Hey " + createdAt)
    }
  }
}