package controllers

//import play.api._
import play.api.mvc.{Action, Controller}
import library.Util
import models.Member
import models.Post

object RssController extends Controller {
  def rss = Action {
    implicit request => {
      val uname = Util.getUnameFromSubdomain(request.domain)
      val memberId = Member.selectByUname(uname).get.id
      val posts = Post.postsByMemberId(memberId)
      // println(posts)
      val rss = 
        <rss version="2.0">
          <channel>
            <title>{uname} のRSS</title>
            <link>http://{request.domain}/</link>
            <description>{uname} のRSS</description>
            <language>ja</language>
          </channel>
          <item>
            <title>title</title>
            <link>https://twitter.com/mahata</link>
            <description>Hello, world!</description>
            <pubData>2013-11-14</pubData>
          </item>
          { posts map {
              p =>
              <item>
                <title>title</title>
                <link>https://twitter.com/mahata</link>
                <description>Hello, world!</description>
                <pubData>2013-11-14</pubData>
              </item>
            }
          }
        </rss>

      // Ok("<dummy>Trying to </dummy>").as("application/rss+xml")
      // Ok("<dummy>Trying to </dummy>")
      Ok(rss)
    }
  }
}
