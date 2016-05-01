package higherorder

/**
  * Created by steve on 4/30/16.
  */



object StayDry {

  case class Email (subject: String,
                    text: String,
                    sender: String,
                    recipient: String)

  type EmailFilter = Email => Boolean
  type SizeConstrain = Int => Boolean

  val sentByOneOf: Set[String] => EmailFilter = senders => email => senders.contains(email.sender)

  //val notByOneOf: Set[String] => EmailFilter = senders => email => !senders.contains(email.sender)
  def complement [A] (predicate: A => Boolean) = (a: A) => !predicate(a)
  val notByOneOf = sentByOneOf andThen (complement(_))


  val sizeConstrain: SizeConstrain => EmailFilter = f => email => f (email.text.length)

//  val minSize: Int => EmailFilter = size => email => email.text.length >= size
//  val maxSize: Int => EmailFilter = size => email => email.text.length <= size
  val minSize: Int => EmailFilter = size => sizeConstrain(_ >= size)
  val maxSize: Int => EmailFilter = size => sizeConstrain(_ <= size)

  def newMailForUser (mails: Seq[Email], f: EmailFilter) = mails filter f

  def any[A](predicates: (A => Boolean)*): A => Boolean = a => predicates.exists(pred => pred(a))
  def none[A](predicates: (A => Boolean)*) = complement(any(predicates: _*))
  def every[A](predicates: (A => Boolean)*) = none(predicates.view.map(complement(_)): _*)
}

