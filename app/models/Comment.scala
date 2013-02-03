package models

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

case class Comment(
    id: Long,
    post_id: Long,
    member_id: Long,
    uname: String,
    content: String,
    is_public: Boolean,
    created_at: Long,
    modified_at: Long)

object Comment {
  val comment = {
    get[Long]("id") ~
    get[Long]("post_id") ~
    get[Long]("member_id") ~
    get[String]("uname") ~
    get[String]("content") ~
    get[Boolean]("is_public") ~
    get[Long]("created_at") ~
    get[Long]("modified_at") map {
      case id ~ post_id ~ member_id ~ uname ~ content ~ is_public ~ created_at ~ modified_at => Comment(id, post_id, member_id, uname, content, is_public, created_at, modified_at)
    }
  }

  def commentsByPostId(postId: Long) = {
    val sql = "SELECT id, post_id, member_id, uname, content, is_public, created_at, modified_at FROM comment WHERE post_id = {post_id}"

    DB.withConnection {
      implicit c => {
        val comments = SQL(sql).on("post_id" -> postId).as(comment *)
        // println(comments)
        comments
      }
    }
  }

  def create(post_id: Long, member_id: Long, uname: String, content: String, isPublic: Boolean) {
    val sql =
      "INSERT INTO comment (post_id, member_id, uname, content, is_public, created_at, modified_at) " +
      "VALUES ({post_id}, {member_id}, {uname}, {content}, {is_public}, {created_at}, {modified_at})"

    DB.withConnection {
      implicit c =>
        SQL(sql).on(
            "post_id" -> post_id,
            "member_id" -> member_id,
            "uname" -> uname,
            "content" -> content,
            "is_public" -> isPublic,
            "created_at" -> System.currentTimeMillis,
            "modified_at" -> System.currentTimeMillis
            ).executeUpdate
    }
  }
}