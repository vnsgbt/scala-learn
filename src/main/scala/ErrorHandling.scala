import java.net.URL

import scala.util.Try

/**
  * Created by steve on 4/19/16.
  */

object ErrorHandling extends App {

  val google = "https://google.com"

  def parseURL (url: String): Try[URL] = Try(new URL(url))

  def ipStream4URL (url: String) = parseURL(url) map { u =>
    Try(u openConnection) map { conn =>
      Try(conn getInputStream)
    }
  }

  printf((parseURL("") toString) + "\n")

  printf(parseURL("") getOrElse parseURL(google) toString)

  printf(parseURL(google) map(_ getProtocol) getOrElse "\nFailed" )


}
