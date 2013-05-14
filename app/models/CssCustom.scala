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
    created_at: Long,
    modified_at: Long)

object CssCustom {
  val cssCustom = {
    get[Long]("id") ~
    get[Long]("member_id") ~
    get[String]("purpose") ~
    get[String]("content") ~
    get[Long]("created_at") ~
    get[Long]("modified_at") map {
      case id~member_id~purpose~content~created_at~modified_at =>
        CssCustom(id, member_id, purpose, content, created_at, modified_at)
    }
  }

  def loadCss(memberId: Long, purpose: String) = {
    val sql = "SELECT id, member_id, purpose, content, created_at, modified_at FROM css-custom WHERE member_id = {member_id} AND purpose = {purpose}"

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
}