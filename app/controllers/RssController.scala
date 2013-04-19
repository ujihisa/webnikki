package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json

object RssController extends Controller {
  def rss(name: String) = Action {
    Ok("Hello, this should be RSS feed, actually. " + name)
  }
}