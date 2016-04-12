package extractor

trait Users {
  def name: String
  def score: Int
}

class FreeUser (val name: String, val score: Int, val upgradable: Double) extends Users
class PremUser (val name: String, val score: Int) extends Users

object User {

  def main (args: Array[String]): Unit = {

    val user = new FreeUser("Steve", 1000, 0.6)
    val puser = new PremUser("Steve", 5000)

    Array(user,puser,0).foreach(user => println(greet(user)))
  }

  def greet (user: Any) = user match {
    case freeUser @ premium() => "Ready to upgrade, " + freeUser.name + "?"
    case PremUser (name, _) => "Welcome back, dear " + name + "!"
    case _ => "You're not a user!"
  }
}

object PremUser {
  def unapply(user: PremUser): Option[(String, Int)] =
    Some((user.name, user.score))
}

object premium {
  def unapply(arg: FreeUser): Boolean = arg.upgradable > 0.5
}