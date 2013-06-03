package models

import anorm._
import anorm.SqlParser._

import play.api.db.DB
import play.api.Play.current

case class Post(
  id: Long,
  member_id: Long,
  title: String,
  content: String,
  created_at: Long,
  published_at: Long)

object Post {
  val post = {
    get[Long]("id") ~
    get[Long]("member_id") ~
    get[String]("title") ~
    get[String]("content") ~
    get[Long]("created_at") ~
    get[Long]("published_at") map {
      case id~member_id~title~content~created_at~published_at => Post(id, member_id, title, content, created_at, published_at)
    }
  }
  val articlesPerPage = 3

  def postsByMemberId(memberId: Long, offset: Option[Int]) = {
    val sql =
      offset match {
        case Some(offset) => s"SELECT id, member_id, title, content, created_at, published_at FROM post WHERE member_id = {member_id} ORDER BY id DESC OFFSET $offset LIMIT $articlesPerPage"
        case _ => "SELECT id, member_id, title, content, created_at, published_at FROM post WHERE member_id = {member_id} ORDER BY id DESC LIMIT $articlesPerPage"
      }

    DB.withConnection {
      implicit c => SQL(sql).on("member_id" -> memberId).as(post *)
    }
  }

  private def countPostsByMemberId(memberId: Long) = {
    DB.withConnection {
      implicit c => SQL("SELECT COUNT(id) FROM post WHERE member_id = {member_id}").on("member_id" -> memberId).as(scalar[Long].single)
    }
  }

  def postByMemberIdAndCreatedAt(memberId: Long, createdAt: Long) = {
    val sql = "SELECT id, member_id, title, content, created_at, published_at FROM post WHERE member_id = {member_id} AND created_at = {created_at}"

    DB.withConnection {
      implicit c => SQL(sql).on("member_id" -> memberId, "created_at" -> createdAt).as(post *).headOption
    }
  }

  def update(
      member_id: Long,
      title: String,
      content: String,
      created_at: Long
      ) = {

    if (content.trim.isEmpty) throw new Exception("記事の内容を入力してください。")

    val sql = "UPDATE post SET title = {title}, content = {content} WHERE member_id = {member_id} AND created_at = {created_at}"

    DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "member_id" -> member_id,
          "title" -> title.trim,
          "content" -> content.trim,
          "created_at" -> created_at
        ).executeUpdate
    }

    created_at
  }

  def create(
    member_id: Long,
    title: String,
    content: String) = {

    if (content.trim.isEmpty) throw new Exception("記事の内容を入力してください。")

    val sql =
      "INSERT INTO post (member_id, title, content, created_at, published_at) " +
      "VALUES ({member_id}, {title}, {content}, {created_at}, {published_at})"
    val created_at = System.currentTimeMillis

    DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "member_id" -> member_id,
          "title" -> title.trim,
          "content" -> content.trim,
          "created_at" -> created_at,
          "published_at" -> created_at
          ).executeUpdate
    }

    created_at
  }

  def isNextPage(memberId: Long, currentPageNum: Int) = {
    val count = countPostsByMemberId(memberId)

    count > (currentPageNum * articlesPerPage)
  }
}
