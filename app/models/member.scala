package models

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current
import library.Random

case class Member(
  id: Long,
  uname: String,
  password: String,
  salt: String,
  email: String)

object Member {
  val member = {
    get[Long]("id") ~
      get[String]("uname") ~
      get[String]("password") ~
      get[String]("salt") ~
      get[String]("email") map {
        case id ~ uname ~ password ~ salt ~ email => Member(
          id, uname, password, salt, email)
      }
  }
  val saltLength = 64
  val strechNum = 1000

  //  def all(): List[Member] = DB.withConnection {
  //    implicit c => SQL("SELECT * FROM member").as(member *)
  //  }

  private def selectBy(field: String, value: String) = {
    DB.withConnection {
      implicit c =>
        {
          val member = SQL("SELECT id FROM member WHERE %s = {value}" format field).on("value" -> value).apply()
          if (member.isEmpty) None else Some(member.head)
        }
    }
  }

  private def stretch(password: String, num: Int): String = {
    num match {
      case 0 => password
      case _ => stretch(password, num - 1)
    }
  }

  def selectByUname(uname: String) =
    selectBy("uname", uname)

  def selectByEmail(email: String) =
    selectBy("email", email)

  def create(
    uname: String,
    password: String,
    email: String) {
    val salt = Random.randomString(saltLength)
    val stretchedPassword = salt
    val sql =
      "INSERT INTO member (uname, password, salt, email) " +
        "VALUES ({uname}, {password}, {salt}, {email})"

    DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "uname" -> uname,
          "password" -> password,
          // "salt" -> salt,
          "salt" -> salt,
          "email" -> email).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection {
      implicit c =>
        SQL("DELETE FROM member WHERE id = {id}").on(
          "id" -> id).executeUpdate()
    }
  }
}
