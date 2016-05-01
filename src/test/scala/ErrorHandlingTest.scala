import java.net.URL

import errorhandling._

import scala.util.{Failure, Success}
import scalatest.UnitSpec

/**
  * Created by vnsjava on 4/24/16.
  */

class ErrorHandlingTest extends UnitSpec {

  val google = "https://google.com"

  val youngCust = Customer(15)
  val error = new ErrorHandlingClass

  "A young customer" should "have age less than 18" in {
    assert(youngCust.age < 18)
  }
  it should "produces UnderAgeException when apply buyCigarettes" in {
    intercept[UnderAgeException] {
      error.buyCigarettes(youngCust)
    }
  }

  "A Try[URL]" should "produce a Success[URL] with valid URL" in {
    error.parseURL(google) shouldBe a [Success[URL]]
  }
  it should "produce a Failure[URL] with invalid URL" in {
    error.parseURL("invalid URL") shouldBe a [Failure[URL]]
  }

  "Mapping a Try[T]" should "result in a Success" in {
    error.parseURL(google).map(_.getProtocol) shouldBe a [Success[String]]
  }
}
