package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._
import models.Post

object PostController extends Controller {
  val postForm = Form(
      tuple(
          "title" -> text,
          "content" -> text
          ))

  def index = Action {
    Ok(html.post(postForm, None))
  }

  def indexPost = Action {
    implicit request =>
      postForm.bindFromRequest.fold(
          formWithErrors => {
            BadRequest(html.post(formWithErrors, None))
          }, {
            case (title, content) => {
              try {
                Post.create(title, content, 0, 0)
                Ok(html.postSuccess(""))
              } catch {
                case e: Exception => {
                  BadRequest(
                      html.post(
                          postForm.bind(Map("title" -> title, "content" -> content)),
                          Some("ここで例外が投げられるのってどういうときなんだろう...ちょっと想像つかないな。")
                          ))
                }
              }
            }
          })
  }
}