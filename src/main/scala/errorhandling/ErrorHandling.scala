package errorhandling
import java.net.URL

import scala.util.Try

/**
  * Created by steve on 4/19/16.
  */

class ErrorHandlingClass {


  def parseURL (url: String): Try[URL] = Try(new URL(url))

  def ipStream4URL (url: String) = parseURL(url) map { u =>
    Try(u openConnection) map { conn =>
      Try(conn getInputStream)
    }
  }

  def buyCigarettes (customer: Customer): Cigarettes =
    if (customer.age < 18) throw UnderAgeException (
      s"Customer must be older than 16, but was ${customer.age}"
    )
    else new Cigarettes
}

class Cigarettes
case class Customer (age: Int)
case class UnderAgeException (message: String) extends Exception(message)
