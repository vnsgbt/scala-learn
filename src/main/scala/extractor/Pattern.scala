package extractor

/**
  * Created by snguy on 4/13/2016.
  */

case class Player (name: String, score: Int) {

  def message (player: Player) = player match {
    case Player (_, score) if score > 100000 => "Get a job, dude!"
    case Player (name, _) => "Hey " + name + ", nice to see you again!"
  }

  def printMessage (player: Player) = println(message(player))
  def currentPlayer() = Player("Steve",5000)


}

object Pattern extends Player("Steve", 7000) {

  def main (args: Array[String]) = {

    /**
      * Patterns in definition
      *  */

    val Player(name, _) = currentPlayer()
    println(name)

    def scores: List[Int] = List(3,5,7,9,0,3,5,6)
    val best :: rest = scores.sortWith((p, c) => p > c)
    println("The score of our champion is " + best)

    def gameResult = ("Steve", 9000)
    val (winner, strike) = gameResult
    println(name + ": " + strike)

    /**
      * Patterns in comprehension
      *  */

    def results = ("Steve", 123) :: ("Steve", 123) :: ("Ko", 456) :: ("Phil", 890) :: Nil

    def hallOfFame = for {
      (name, scores)  <- results
      if scores > 123
    } yield name

    println(hallOfFame)

    /**
      * Patterns in filter
      *  */

    val lists = List (1,2,3) :: List.empty :: List (5,9) :: Nil
    println(for {
      list @ head :: _ <- lists
    } yield list.size)
  }
}
