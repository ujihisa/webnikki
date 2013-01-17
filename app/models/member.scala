package models

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

case class Member(
  // id: Pk[Long],
  id: Long,
  uname: String,
  password: String,
  password_salt: String,
  email: String
)

object Member {
  val member = {
    get[Long]("id") ~
    get[String]("uname") ~
    get[String]("password") ~
    get[String]("password_salt") ~
    get[String]("email") map {
      case id~uname~password~password_salt~email => Member(
        id, uname, password, password_salt, email
      )
    }
  }

//  def all(): List[Member] = DB.withConnection {
//    implicit c => SQL("SELECT * FROM member").as(member *)
//  }

  // this should be private method
  def selectBy(field: String, value: String) = {
    DB.withConnection {
      implicit c => {
        val member = SQL("SELECT id FROM member WHERE {field}= {value}").on(field -> value).apply()
        if (member.isEmpty) None else Some(member.head)
      }
    }
  }

  def selectByUname(uname: String) = {
//    DB.withConnection {
//      implicit c => {
//        val member = SQL("SELECT id FROM member WHERE uname = {uname}").on("uname" -> uname).apply()
//        if (member.isEmpty) None else Some(member.head)
//      }
//    }
    selectBy("uname", uname)
  }

  def selectByEmail(email: String) = {
  }

  def create(
    uname: String,
    password: String,
    email: String) {
    val password_salt = "tekito-"
    val sql =
      "INSERT INTO member (uname, password, password_salt, email) " +
      "VALUES ({uname}, {password}, {password_salt}, {email})"

    DB.withConnection {
      implicit c => SQL(sql).on(
        "uname" -> uname,
        "password" -> password,
        "password_salt" -> password_salt,
        "email" -> email
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection {
      implicit c => SQL("DELETE FROM member WHERE id = {id}").on(
        "id" -> id
      ).executeUpdate()
    }
  }
}
