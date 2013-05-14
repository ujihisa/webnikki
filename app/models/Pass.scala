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
      case id~email~token~validity~created_at =>
        Pass(id, email, token, validity, created_at)
    }
  }

  val twoHours = 7200000

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

  def checkTokenValidity(token: String) = {
    val sql = "SELECT id, email, token, validity, created_at FROM pass_reset WHERE token = {token}"

    val passReset = DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "token" -> token
        ).as(pass *).headOption
    }

    val errorMessage = passReset match {
      case Some(x) => {
        if (!x.validity) {
          "使用済みの token です"
        } else if (twoHours < (System.currentTimeMillis - x.created_at)) {
          "期限切れの token です"
        } else {
          ""
        }
      }
      case _ => "存在しない token です"
    }

    errorMessage match {
      case "" => Map("isValid" -> true, "email" -> passReset.get.email)
      case _  => Map("isValid" -> false, "message" -> errorMessage)
    }
  }

  def invalidateToken(token: String) = {
    val sql = "UPDATE pass_reset SET validity = false WHERE token = {token}"
    DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "token" -> token
        ).executeUpdate
    }
  }
}