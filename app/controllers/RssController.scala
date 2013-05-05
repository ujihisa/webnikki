package controllers

//import play.api._
import play.api.mvc.{Action, Controller}
import library.Util
import models.Member
import models.Post
import com.tristanhunt.knockoff.DefaultDiscounter.{knockoff, toXHTML}

object RssController extends Controller {
  def rss = Action {
    implicit request => {
      val uname = Util.getUnameFromSubdomain(request.domain)
      val memberId = Member.selectByUname(uname).get.id
      val posts = Post.postsByMemberId(memberId)
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
                <title>{p.title}</title>
                <link>https://twitter.com/mahata</link>
                <description>
                { "__CDATA_START__" }
                { toXHTML(knockoff(p.content)) }
                { "__CDATA_END__" }
                </description>
                <pubData>2013-11-14</pubData>
              </item>
            }
          }
        </rss>

      Ok(
        rss.toString.
        replace("__CDATA_START__", "<![CDATA[").
        replace("__CDATA_END__", "]]>")
      ).as("application/rss+xml")
    }
  }
}
