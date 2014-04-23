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

  def loadCss(memberId: Long, purpose: String) = loadCustomData(memberId, purpose, "css")
  def loadJs(memberId: Long, purpose: String) = loadCustomData(memberId, purpose, "js")

  private def loadCustomData(memberId: Long, purpose: String, contentType: String) = {
    if (purpose != "list" && purpose != "page")     throw new Exception("purpose は list か page のみサポートしています。")
    if (contentType != "css" && contentType!= "js") throw new Exception("contentType は css か jsのみサポートしています。")

    val sql = "SELECT id, member_id, CAST(content_purpose AS TEXT), CAST(content_type AS TEXT), content, modified_at FROM custom_data WHERE member_id = {member_id} AND content_purpose = CAST({content_purpose} AS purpose) " + (if (contentType == "css") "AND content_type = CAST('css' AS ctype)" else "AND content_type = CAST('js' AS ctype)")

    val code = DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "member_id" -> memberId,
          "content_purpose" -> purpose
        ).as(customData *).headOption
    }

    code match {
      case Some(x) => x.content
      case _ => ""
    }
  }

  def saveCss(memberId: Long, purpose: String, content: String) = saveCustomData(memberId, purpose, content, "css")
  def saveJs(memberId: Long, purpose: String, content: String) = saveCustomData(memberId, purpose, content, "js")

  private def saveCustomData(memberId: Long, purpose: String, content: String, contentType: String) = {
    val oldContent = loadCustomData(memberId, purpose, contentType)

    val sql = oldContent match {
      case "" => "INSERT INTO custom_data (member_id, content_purpose, content_type, content, modified_at) VALUES ({member_id}, CAST({content_purpose} AS purpose), CAST({content_type} AS ctype), {content}, {modified_at})"
      case _  => "UPDATE custom_data SET content = {content}, modified_at = {modified_at} WHERE member_id = {member_id} AND content_purpose = CAST({content_purpose} AS purpose) AND content_type = CAST({content_type} AS ctype)"
    }

    DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "member_id" -> memberId,
          "content_purpose" -> purpose,
          "content_type" -> contentType,
          "content" -> content,
          "modified_at" -> System.currentTimeMillis
        ).executeUpdate
    }
  }
}
