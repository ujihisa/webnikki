package models

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

case class Post(
  id: Long,
  member_id: Long,
  title: String,
  content: String,
  created_at: Long,
  modified_at: Long)

object Post {
  val post = {
    get[Long]("id") ~
    get[Long]("member_id") ~
    get[String]("title") ~
    get[String]("content") ~
    get[Long]("created_at") ~
    get[Long]("modified_at") map {
      case id ~ member_id ~ title ~ content ~ created_at ~ modified_at => Post(id, member_id, title, content, created_at, modified_at)
    }
  }

  def postsByMemberId(memberId: Long) = {
    val sql = "SELECT id, member_id, title, content, created_at, modified_at FROM post WHERE member_id = {member_id}"

    DB.withConnection {
      implicit c => SQL(sql).on("member_id" -> memberId).as(post *)
    }
  }

  def postByMemberIdAndCreatedAt(memberId: Long, createdAt: Long) = {
    val sql = "SELECT id, member_id, title, content, created_at, modified_at FROM post WHERE member_id = {member_id} AND created_at = {created_at}"

    DB.withConnection {
      implicit c => SQL(sql).on("member_id" -> memberId, "created_at" -> createdAt).as(post *).headOption
    }
  }

  def create(
    member_id: Long,
    title: String,
    content: String) {

    if (content.trim.isEmpty) throw new Exception("記事の内容を入力してください。")

    val sql =
      "INSERT INTO post (member_id, title, content, created_at, modified_at) " +
      "VALUES ({member_id}, {title}, {content}, {created_at}, {modified_at})"

    DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "member_id" -> member_id,
          "title" -> title.trim,
          "content" -> content.trim,
          "created_at" -> (System.currentTimeMillis / 1000).toLong,
          "modified_at" -> (System.currentTimeMillis / 1000).toLong
          ).executeUpdate
    }
  }
}
