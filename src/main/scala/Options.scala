/**
  * Created by snguy on 4/14/2016.
  */

case class User
(id: Int, first: String, last: String, age: Int, gender: Option[String])

object userRepo {
  private val users =
    Map(1 -> User(1, "John", "Doe", 32, Some("male")),
      2 -> User(2, "Johana", "Duh", 30, None))

  def findId (id: Int): Option[User] = users.get(id)
  def findAll = users.values
}

object Options extends App {

  val greeting: Option[String] = Some("Hello world!")
  val greetingNone: Option[String] = None

  val user1 = userRepo.findId(1)
  val user2: User = userRepo.findId(2).getOrElse(User(2,"a","b",3,None))

  case class Resource (content: String)
  val resourceFromConfig: Option[Resource] = None
  val resourceFromPath: Option[Resource] = Some(Resource("I'm a resource."))

  val gender = user2.gender match {
    case Some(gender) => "Gender: " + gender
    case None => "Gender: not specified"
  }

  if (user1.isDefined)
    println ("\nClunky way: \n" + user1 + "\n name: " + user1.get.first)

  println("\nfallback way: \n Gender: " + user2.gender.getOrElse("Not specified"))
  println("\nOption pattern matching since Some is also a case: \n" + gender)

  userRepo.findId(2).foreach(user => println("\nOption is like collection: \n"
    + user.gender.getOrElse("notSpecified")))

  println("\nOption can be mapped: \n" +
    userRepo.findId(1).map(_.age))

  println("\nMapping an Option to Option is chained: \n + " +
    userRepo.findId(1).map(_.gender))

  println("\nUsing flatMap: \n" +
    userRepo.findId(1).flatMap(_.gender) + "\n" +
    userRepo.findId(2).flatMap(_.gender) + "\n" +
    userRepo.findId(3).flatMap(_.gender) + "\n"
  )
  println("\nUsing filter: \n" +
    userRepo.findId(1).filter(_.age > 30) + "\n" +
    userRepo.findId(2).filter(_.age > 30) + "\n" +
    userRepo.findId(3).filter(_.age > 30) + "\n"
  )

  println("\nComprehension: \n" +
    (for {
      user <- userRepo.findId(1)
      gender <- user.gender
    } yield gender))

  println("\nComprehension flat: \n" +
    (for {
      user <- userRepo.findAll
      gender <- user.gender
    } yield gender))

  println("\nLeft side generator: \n" +
    (for {
      User(_,_,_,_,Some(gender)) <- userRepo.findAll
    } yield gender))


  println("\nChaining Options: \n" +
    (resourceFromConfig orElse resourceFromPath)
  )
}


