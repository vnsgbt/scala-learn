/**
  * Created by SNGUY on 4/12/2016.
  */
object Classes {
  def main (args: Array[String]): Unit = {
    val pt = new Point(1,2)
    println(pt)
    pt.move(10,10)
    println(pt)
  }
}

class Point (xc: Int, yc: Int) {
  var x = xc
  var y = yc

  def move (dx: Int, dy: Int): Unit = {
    x += dx
    y += dy
  }
  override def toString: String = "(" + x + ", " + y + ")"
}