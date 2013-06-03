package controllers

import play.api.mvc.{Action, Controller}
import library.Util
import models.Member
import models.Post
import java.text.SimpleDateFormat
import java.util.Date
import com.tristanhunt.knockoff.DefaultDiscounter.{knockoff, toXHTML}

object RssController extends Controller {
  def rss = Action {
    implicit request => {
      val uname = Util.getUnameFromSubdomain(request.domain)
      val memberId = Member.selectByUname(uname).get.id
      val posts = Post.postsByMemberId(memberId, null)
      val rss =
        <rss version="2.0">
          <channel>
            <title>{uname} のRSS</title>
            <link>http://{request.domain}/</link>
            <description>{uname} のRSS</description>
            <language>ja</language>
          </channel>
          { posts map {
              p =>
              <item>
                <title>{p.title}</title>
                <link>http://{ request.domain }/entry/{ p.created_at }</link>
                <description>
                { "__CDATA_START__" }
                { toXHTML(knockoff(p.content)) }

                { "__CDATA_END__" }
                </description>
                <pubData>{
                  val d = new Date(p.published_at.toLong)
                  val output = new SimpleDateFormat("yyyy/MM/dd (E) HH:mm:ss z")
                  output.format(d)
                }</pubData>
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
