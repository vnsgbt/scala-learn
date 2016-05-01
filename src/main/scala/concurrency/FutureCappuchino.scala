package concurrency

import concurrency.CoffeeShopType._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Random, Success, Try}

/**
  * Created by vnsjava on 4/27/16.
  */

object CoffeeShopType {
  type CoffeeBeans = String
  type GroundCoffee = String
  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuchino = String

  case class Water (temperature: Int)

  def temperatureOK (water: Water): Future[Boolean] = Future {
    println("Measuring temperature...")
    Thread.sleep(2000)
    println("Done measuring temperature...")

    (80 to 85) contains water.temperature
  }
}

object FutureCallbacks extends App {
  (new FutureCappuchino).grindFt("baked beans").onComplete {
    case Success(ground) => println(s"got my $ground")
    case Failure(ex) => println(ex.getMessage)
  }
  Thread.sleep(2000)
}

object ComposeFutures extends App {

  val cappuchino = new FutureCappuchino

  cappuchino heatWaterFt Water(25) map { water =>
    println("We're in the heatWater's future")
    (80 to 85) contains water.temperature
  }

  Thread.sleep(2000)

  cappuchino heatWaterFt Water(25) map {
    water => temperatureOK(water)
  }

  println("cappuchino: I'm waiting...")
  Thread.sleep(4000)

  cappuchino heatWaterFt Water(25) flatMap {
    water => temperatureOK(water)
  }
  Thread.sleep(4000)


  Thread.sleep(4000)

}

class FutureCappuchino {

  def heatWater (water: Water): Water = water.copy(temperature = 85)
  def grind (beans: CoffeeBeans): GroundCoffee = s"Ground coffee of $beans"
  def frothMilk (milk: Milk): FrothedMilk = s"Frothed $milk"
  def brew (coffee: GroundCoffee, heatedWater: Water): Espresso = "Espresso"
  def combine (espresso: Espresso, frothedMilk: FrothedMilk): Cappuchino = "Cappuccino"

  case class GrindException (msg: String) extends Exception (msg)
  case class FrothException (msg: String) extends Exception (msg)
  case class BoilsException (msg: String) extends Exception (msg)
  case class BrewsException (msg: String) extends Exception (msg)

  def makeCappuchino (): Try[Cappuchino] = for {
    ground <- Try (grind("SEA beans"))
    water <- Try (heatWater(Water(25)))
    espresso <- Try (brew(ground,water))
    foam <- Try (frothMilk("whole milk"))
  }
    yield combine(espresso,foam)

  def grindFt (beans: CoffeeBeans): Future[GroundCoffee] = Future {
    println("Start grinding...")
    Thread.sleep(Random.nextInt(2000))
    if (beans == "baked beans") throw GrindException("Are you joking?")
    println("Finished grinding...")
    s"Ground coffee of $beans"
  }

  def heatWaterFt (water: Water): Future[Water] = Future {
    println("Start heating water...")
    Thread.sleep(Random.nextInt(2000))
    println("Hot, it's hot!")
    water.copy(temperature = 85)
  }

  def frothMilkFt (milk: Milk): Future[FrothedMilk] = Future {
    println("Milk frothing system engaged!")
    Thread.sleep(Random.nextInt(2000))
    println("Finished frothing...")
    s"Frothed $milk"
  }

  def brewFt (coffee: GroundCoffee, heatedWater: Water): Future[Espresso] = Future {
    println("Brewing...")
    Thread.sleep(Random.nextInt(2000))
    println("It's brewed...")
    "Espresso"
  }

}
