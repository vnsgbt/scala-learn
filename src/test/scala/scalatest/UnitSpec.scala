package scalatest

import org.scalatest._

/**
  * Created by vnsjava on 4/26/16.
  */

abstract class UnitSpec extends FlatSpec
  with Matchers
  with OptionValues
  with Inside
  with Inspectors
