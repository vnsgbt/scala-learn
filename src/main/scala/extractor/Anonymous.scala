package extractor

/**
  * Created by snguy on 4/13/2016.
  */

object Anonymous extends App {

  val wordFreqs = ("habitual", 6) :: ("and", 56) :: ("consuetudinary", 2) ::
    ("additionally", 27) :: ("homely", 5) :: ("society", 13) :: Nil

  def wordWithoutOutliners (wordFreq: Seq[(String,Int)]): Seq[String] =
    wordFreq filter predicate map transform

  val predicate: ((String, Int)) => Boolean = { case (_, f) => f > 3 && f < 25}
  val transform: ((String, Int)) => String = { case (w, _) => w}

  /**
    * Partial Function
    * */


  val pf = new PartialFunction[(String, Int), String] {
    def apply (wordFreq: (String, Int)) = wordFreq match {
      case (word, freq) if freq > 2 && freq < 25 => word
    }
    def isDefinedAt(x: (String, Int)): Boolean = x match {
      case (word, freq) if freq > 3 && freq < 25 => true
      case _ => false
    }
  }

  println("Seq's collect: " + wordFreqs.collect(pf))

  println("partialFunc: " + wordFreqs.collect {
    case (word, freq) if freq > 3 && freq < 25 => word
  })
}
