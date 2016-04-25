package scalatest.flatspect

import org.scalatest.FlatSpec

/**
  * Created by vnsjava on 4/25/16.
  */

class SetSpec extends FlatSpec {

  "An empty Set" should "have size 0" in {
    assert(Set.empty.isEmpty)
  }

  it should "produce NoSuchElementException when head is invoked" in {
    intercept[NoSuchElementException] {
      Set.empty.head
    }
  }
}
