package models

import anorm._
import anorm.SqlParser._

import play.api.db.DB
import play.api.Play.current

case class CssCustom(
    id: Long,
    member_id: Long,
    purpose: String,
    content: String,
    modified_at: Long)

object CssCustom {
  val cssCustom = {
    get[Long]("id") ~
    get[Long]("member_id") ~
    get[String]("purpose") ~
    get[String]("content") ~
    get[Long]("modified_at") map {
      case id~member_id~purpose~content~modified_at =>
        CssCustom(id, member_id, purpose, content, modified_at)
    }
  }

  def loadCss(memberId: Long, purpose: String) = {
    val sql = "SELECT id, member_id, purpose, content, modified_at FROM css-custom WHERE member_id = {member_id} AND purpose = {purpose}"

    val css = DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "member_id" -> memberId,
          "purpose" -> purpose
        ).as(cssCustom *).headOption
    }

    css match {
      case Some(x) => x.content
      case _ => ""
    }
  }

  def saveCss(memberId: Long, purpose: String, css: String) = {
    // TODO: css が空文字であれば何もしないか例外を投げる 

    val css = loadCss(memberId, purpose)
    val sql = css match {
      case "" => "INSERT INTO css_custom (member_id, purpose, content, modified_at) VALUES ({member_id}, {purpose}, {content}, {modified_at})"
      case _  => "UPDATE css_custom SET content = {content}, modified = {modified} WHERE member_id = {member_id} AND purpose = {purpose}"
    }

    DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "member_id" -> memberId,
          "purpose" -> purpose,
          "content" -> css,
          "modified_at" -> System.currentTimeMillis
        ).executeUpdate
    }
  }
}