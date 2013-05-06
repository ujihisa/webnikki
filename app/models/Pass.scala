package models

import anorm._
import anorm.SqlParser._

import play.api.db.DB
import play.api.Play.current

case class Pass(
    id: Long,
    email: String,
    token: String,
    validity: Boolean,
    created_at: Long)

object Pass {
  val pass = {
    get[Long]("id") ~
    get[String]("email") ~
    get[String]("token") ~
    get[Boolean]("validity") ~
    get[Long]("created_at") map {
      case id~email~token~validity~created_at => Pass(id, email, token, validity, created_at)
    }
  }

  def addOnetimeToken = {
    
  }
}