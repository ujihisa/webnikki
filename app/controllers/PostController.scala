package controllers

import play.api._
import play.api.mvc._

import views._

object PostController extends Controller {
  def index = Action {
    Ok("作ってる")
  }

  def indexPost = TODO
}