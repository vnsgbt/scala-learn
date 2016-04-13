package extractor

/**
  * Created by snguy on 4/13/2016.
  */

object Anonymous {

  val wordFreqs = ("habitual", 6) :: ("and", 56) :: ("consuetudinary", 2) ::
    ("additionally", 27) :: ("homely", 5) :: ("society", 13) :: Nil

  def wordWithoutOutliners (wordFreq: Seq[(String,Int)]): Seq[String] =
    wordFreq filter predicate map transform

  val predicate: ((String, Int)) => Boolean = { case (_, f) => f > 3 && f < 25}
  val transform: ((String, Int)) => String = { case (w, _) => w}
}
