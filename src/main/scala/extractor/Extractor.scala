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

    /**
      *  Infix notation
      * */

    val xs = 58 #:: 43 #:: 93 #:: Stream.empty

    println(xs match {
      case first #:: second #:: _ => first - second
      case _ => -1
    })

    println(xs match {
      case #:: (first, #:: (second,_)) => first + second
    })

    /**
      * matching wildcard
      * */

    val zs = 3 ::  11 :: 7 :: Nil

    println(zs match {
      case List(a,b,_*) => a * b
      case List(a,b,c) => a + b + c
      case _ => 0
    })

    /**
      * dynamic binding
      * */

    println(greetWithFirstName("Steve Nguyen"))
    println(greetWithFullName("Steve Tan Nguyen"))
  }

  /*********************************/

  def greet (user: Any) = user match {
    case freeUser @ premium() => "Ready to upgrade, " + freeUser.name + "?"
    case PremUser (name, _) => "Welcome back, dear " + name + "!"
    case _ => "You're not a user!"
  }

  def greetWithFirstName (names: String) = names match {
    case GivenNames(firstName, _*) => "Good morning, " + firstName + "!"
    case _ => "Welcome! Please make sure to fill in your name!"
  }
  def greetWithFullName (names: String) = names match {
    case Names(last, first, _*) => "Good morning, " + first + " " + last + "!"
    case _ => "Welcome! Please make sure to fill in your name!"
  }
}

object PremUser {
  def unapply(user: PremUser): Option[(String, Int)] =
    Some((user.name, user.score))
}

object premium {
  def unapply(arg: FreeUser): Boolean = arg.upgradable > 0.5
}

object GivenNames {
  def unapplySeq (name: String): Option[Seq[String]] = {
    val names = name.trim.split(" ")
    if (names.forall(_.isEmpty)) None else Some(names)
  }
}

object Names {
  def unapplySeq(name: String): Option[(String, String, Array[String])] = {
    val names = name.trim.split(" ")
    if (names.size < 2) None
    else Some((names.last, names.head, names.drop(1).dropRight(1)))
  }
}