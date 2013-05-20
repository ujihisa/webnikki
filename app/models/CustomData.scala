package models

import anorm._
import anorm.SqlParser._

import play.api.db.DB
import play.api.Play.current

case class CustomData(
    id: Long,
    member_id: Long,
    content_purpose: String,
    content_type: String,
    content: String,
    modified_at: Long)

object CustomData {
  val customData = {
    get[Long]("id") ~
    get[Long]("member_id") ~
    get[String]("content_purpose") ~
    get[String]("content_type") ~
    get[String]("content") ~
    get[Long]("modified_at") map {
      case id~member_id~content_purpose~content_type~content~modified_at =>
        CustomData(id, member_id, content_purpose, content_type, content, modified_at)
    }
  }

  def loadCss(memberId: Long, purpose: String) = {
    val sql = "SELECT id, member_id, CAST(content_purpose AS TEXT), CAST(content_type AS TEXT), content, modified_at FROM custom_data WHERE member_id = {member_id} AND content_purpose = CAST({content_purpose} AS purpose)"

    val css = DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "member_id" -> memberId,
          "content_purpose" -> purpose
        ).as(customData *).headOption
    }
    println(sql, memberId, purpose)

    css match {
      case Some(x) => x.content
      case _ => ""
    }
  }

  def saveCss(memberId: Long, purpose: String, css: String) = {
    if (css.isEmpty) throw new Exception("CSS の内容を入力してください。")

    val oldCss = loadCss(memberId, purpose)

    val sql = oldCss match {
      case "" => "INSERT INTO custom_data (member_id, content_purpose, content_type, content, modified_at) VALUES ({member_id}, CAST({content_purpose} AS purpose), 'css', {content}, {modified_at})"
      case _  => "UPDATE custom_data SET content = {content}, modified_at = {modified_at} WHERE member_id = {member_id} AND content_purpose = CAST({content_purpose} AS purpose)"
    }

    DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "member_id" -> memberId,
          "content_purpose" -> purpose,
          "content" -> css,
          "modified_at" -> System.currentTimeMillis
        ).executeUpdate
    }
  }
}
