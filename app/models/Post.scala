package models

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

case class Post(
  id: Long,
  title: String,
  content: String,
  created_at: Long,
  modified_at: Long)

object Post {
  val post = {
    get[Long]("id") ~
    get[String]("title") ~
    get[String]("content") ~
    get[Long]("created_at") ~
    get[Long]("modified_at") map {
      case id ~ title ~ content ~ created_at ~ modified_at => Post(id, title, content, created_at, modified_at)
    }
  }

  def create(
    title: String,
    content: String) {

    if (content.trim.isEmpty) throw new Exception("記事の内容を入力してください。")

    val sql =
      "INSERT INTO post (title, content, created_at, modified_at) " +
      "VALUES ({title}, {content}, {created_at}, {modified_at})"

    DB.withConnection {
      implicit c =>
        SQL(sql).on(
          "title" -> title.trim,
          "content" -> content.trim,
          "created_at" -> (System.currentTimeMillis / 1000).toLong,
          "modified_at" -> (System.currentTimeMillis / 1000).toLong
          ).executeUpdate
    }
  }
}
