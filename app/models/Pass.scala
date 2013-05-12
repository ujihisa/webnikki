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

  def addOnetimeToken(email: String, token: String) = {
    val sql =
      "INSERT INTO pass_reset (email, token, created_at) " +
      "VALUES ({email}, {token}, {created_at})"
    val created_at = System.currentTimeMillis

    DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "email" -> email,
          "token" -> token,
          "created_at" -> created_at
        ).executeUpdate
    }

    created_at
  }

  def isValidToken(token: String) = {
    val sql = "SELECT validity, created_at FROM pass_reset WHERE token = {token}"

    val foo = DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "token" -> token
        ).as(pass *).headOption
    }

    println(foo)
  }
}